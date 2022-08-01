package com.example.foodprint

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.roundToInt


class SearchInfoActivity : AppCompatActivity() {
    lateinit var tvFoodName: TextView
    lateinit var pBarChoc: ProgressBar
    lateinit var pBarProt: ProgressBar
    lateinit var pBarFat: ProgressBar
    lateinit var tvChoc: TextView
    lateinit var tvProt: TextView
    lateinit var tvFat: TextView
    lateinit var tvMoreInfo: TextView
    lateinit var tvKcal: TextView

    lateinit var dbmanger: DBManager
    lateinit var sqLitedb: SQLiteDatabase

    lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_info)

        //하단 네비게이션 호출
        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView

        //화면 전환하면서 받아온 검색이름 저장
        val intent = intent
        val str_name = intent.extras!!.getString("intent_name")
        val userName = intent.extras!!.getString("name")

        //각 요소와 연결
        tvFoodName = findViewById(R.id.tvFoodName)
        pBarChoc = findViewById(R.id.pBarChoc)
        pBarProt = findViewById(R.id.pBarProt)
        pBarFat = findViewById(R.id.pBarFat)
        tvChoc = findViewById(R.id.tvChoc)
        tvProt = findViewById(R.id.tvProt)
        tvFat = findViewById(R.id.tvFat)
        tvMoreInfo = findViewById(R.id.tvMoreInfo)
        tvKcal = findViewById(R.id.tvKcal)

        layout = findViewById(R.id.layout)

        tvFoodName.setText(str_name)

        pBarChoc.max = 300
        pBarChoc.progress = 150

        //DB연결
        dbmanger = DBManager(this, "foodlistDB", null, 1)
        sqLitedb = dbmanger.readableDatabase

        var cursor: Cursor

        //음식명을 검색해서 모든 정보 가져오기
        cursor = sqLitedb.rawQuery("SELECT * FROM foodlistDB WHERE name = '" + str_name + "';", null)

        //정보 저장해서 출력
        while(cursor.moveToNext()){
            //1회 섭취참고량(칼로리)
            var servsize = cursor.getInt(cursor.getColumnIndex("servsize"))

            tvKcal.text = "총 칼로리 :                                " + servsize.toString() + "Kcal"

            //변환 전에는 100g당 포함된 수치임 //국같은 경우 낮게 나오니 다른 음식으로 검색하기
            var str_Choc = cursor.getDouble(cursor.getColumnIndex("chocdf"))
            var str_Prot = cursor.getDouble(cursor.getColumnIndex("prot"))
            var str_Fat = cursor.getDouble(cursor.getColumnIndex("fatce"))

            //max의 값은 19~29 여성을 기준으로 하루 권장섭취량을 만들어 놓았음
            pBarChoc.max = 130
            pBarChoc.progress = str_Choc.toInt()
            pBarProt.max = 50
            pBarProt.progress = str_Prot.toInt()
            pBarFat.max = 25
            pBarFat.progress = str_Fat.toInt()

            //소수점 첫째자리까지 출력
            tvChoc.text = str_Choc.toString() + "/130g"
            tvProt.text = str_Prot.toString() + "/50g"
            tvFat.text = str_Fat.toString() + "/25g"


            //주요정보 이외의 값 받아오기
            var sugar = cursor.getDouble(cursor.getColumnIndex("sugar"))
            var ca = cursor.getDouble(cursor.getColumnIndex("ca"))
            var fe = cursor.getDouble(cursor.getColumnIndex("fe"))
            var p = cursor.getDouble(cursor.getColumnIndex("p"))
            var k = cursor.getDouble(cursor.getColumnIndex("k"))
            var vita = cursor.getDouble(cursor.getColumnIndex("vita"))
            var vitc = cursor.getDouble(cursor.getColumnIndex("vitc"))
            var vitd = cursor.getDouble(cursor.getColumnIndex("vitd"))
            var chole = cursor.getDouble(cursor.getColumnIndex("chole"))
            var fasat = cursor.getDouble(cursor.getColumnIndex("fasat"))
            var fatrn = cursor.getDouble(cursor.getColumnIndex("fatrn"))

            //모두 합쳐 문자열 생성 후 텍스트뷰에 넣기
            var str_tvl: String
            str_tvl = "\r\n" + "당류(g)    :    " + sugar.toString() + "\r\n" +
                    "칼슘(mg)    :    " + ca.toString() + "\r\n" +
                    "철(mg)    :    " + fe.toString() + "\r\n" +
                    "인(mg)    :    " + p.toString() + "\r\n" +
                    "나트륨(mg)    :    " + k.toString() + "\r\n" +
                    "비타민A(μg)    :    " + vita.toString() + "\r\n" +
                    "비타민C(μg)    :    " + vitc.toString() + "\r\n" +
                    "비타민D(μg)    :    " + vitd.toString() + "\r\n" +
                    "콜레스테롤(mg)    :    " + chole.toString() + "\r\n" +
                    "총포화지방산(g)    :    " + fasat.toString() + "\r\n" +
                    "트렌스지방(g)    :    " + fatrn.toString() + "\r\n"

            tvMoreInfo.setText(str_tvl)

            //추천하는 식단 이미지 넣기
            if(str_name == "김치찌개_꽁치"){
                var layout_items: LinearLayout = LinearLayout(this)
                layout_items.orientation = LinearLayout.VERTICAL

                var Image: ImageView = ImageView(this)
                Image.setImageResource(R.drawable.kimchi)

                layout_items.addView(Image)
                layout.addView(layout_items)
            }
            else if(str_name == "미역국"){
                var layout_items: LinearLayout = LinearLayout(this)
                layout_items.orientation = LinearLayout.VERTICAL

                var Image: ImageView = ImageView(this)
                Image.setImageResource(R.drawable.seaweed)

                layout_items.addView(Image)
                layout.addView(layout_items)
            }
        }

        //하단화면 이동
        bottomNavi.selectedItemId = R.id.action_search
        // Perform item selected listener
        bottomNavi.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_calendar -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_recipe -> {
                    startActivity(Intent(applicationContext, RecipeActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_search ->  {
                    startActivity(Intent(applicationContext, SearchActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_mypage -> {
                    startActivity(Intent(applicationContext, MyPageActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
}