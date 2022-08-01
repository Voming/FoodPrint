package com.example.foodprint

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchActivity : AppCompatActivity() {
    lateinit var btnSearch: Button
    lateinit var edtFood: EditText
    lateinit var logo1: ImageView
    lateinit var logo2: ImageView

    lateinit var dbmanger: DBManager
    lateinit var sqLitedb: SQLiteDatabase
    lateinit var layout: LinearLayout

    lateinit var searchStr: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val intent = intent
        val userName = intent.extras!!.getString("name")

        //하단 네비게이션 호출
        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView

        //각각의 변수와 연결
        btnSearch = findViewById(R.id.btnSearch)
        edtFood = findViewById(R.id.edtFood)
        logo1 = findViewById(R.id.imageViewlogo1)
        logo2 = findViewById(R.id.imageViewlogo2)

        layout = findViewById(R.id.foodlist)

        //검색버튼 클릭시
        btnSearch.setOnClickListener {
            //이전 검색내용 지우기
            layout.removeAllViews()
            //배경 이미지 지우기
            logo1.visibility = View.GONE
            logo2.visibility = View.GONE

            //검색 문자열 받아오기
            searchStr = edtFood.text.toString()

            //DB생성 후 읽어오기로 뭐치문 작성
            dbmanger = DBManager(this, "foodlistDB", null, 1)
            sqLitedb = dbmanger.readableDatabase

            var cursor: Cursor

            cursor = sqLitedb.rawQuery("SELECT * FROM foodlistDB WHERE bigname = '" + searchStr + "';", null)

            var num: Int = 0
            //검색한 내용의 모든 데이터의 이름으로 텍스트뷰를 생성하여 레이이아웃에 추가
            while(cursor.moveToNext()){
                var str_name = cursor.getString(cursor.getColumnIndex("name")).toString()

                var layout_items: LinearLayout = LinearLayout(this)
                layout_items.orientation = LinearLayout.VERTICAL
                layout_items.id = num
                layout_items.setTag(str_name)

                layout.setPadding(20,0,20,0);

                var tvName: TextView = TextView(this)
                tvName.text = str_name
                tvName.textSize = 18f
                tvName.setPadding(20,40,40,40);
                //둥근 모서리 테마를 적용하기 위함
                tvName.setBackgroundDrawable(getResources().getDrawable(R.drawable.edge));
                layout_items.addView(tvName)

                //텍스트뷰 클릭시 해당 상세페이지로 이동, 이름값 전달
                layout_items.setOnClickListener {
                    val intent = Intent(this, SearchInfoActivity::class.java)
                    intent.putExtra("intent_name", str_name)
                    intent.putExtra("name", userName)
                    startActivity(intent)
                }

                layout.addView(layout_items)
                num++
            }
            cursor.close()
            sqLitedb.close()
            dbmanger.close()
        }


        //하단화면 이동
        bottomNavi.selectedItemId = R.id.action_search
        // Perform item selected listener
        bottomNavi.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_calendar -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("name", userName)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_recipe -> {
                    val intent = Intent(this, RecipeActivity::class.java)
                    intent.putExtra("name", userName)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_search -> return@OnNavigationItemSelectedListener true
                R.id.action_mypage -> {
                    val intent = Intent(this, MyPageActivity::class.java)
                    intent.putExtra("name", userName)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
}