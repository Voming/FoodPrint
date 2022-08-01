package com.example.foodprint

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyPageActivity : AppCompatActivity() {

    // 연결
    lateinit var tvUserName: TextView
    lateinit var editBtn_body: ImageButton
    lateinit var height: TextView
    lateinit var weight: TextView
    lateinit var rate: TextView
    lateinit var editBtn_goal: ImageButton
    lateinit var editBtn_alert: ImageButton
    lateinit var btn_removeAlert: Button
    lateinit var btn_editalert: Button

    // DB
    //lateinit var dbManager: AlertDBManager
    //lateinit var sqlDB: SQLiteDatabase

    // File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        //사용자 이름 받아오기
        val intent = intent
        val userName = intent.extras!!.getString("name")


        // 하단 네이게이션
        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView
        // 연결
        tvUserName =findViewById(R.id.tvUserName)
        editBtn_body = findViewById(R.id.editBtn_body)
        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        rate = findViewById(R.id.rate)
        editBtn_goal = findViewById(R.id.editBtn_goal)
        editBtn_alert = findViewById(R.id.editBtn_alert)

        //사용자 이름
        tvUserName.setText(userName)

        //dbManager = AlertDBManager(this, "alertDB", null, 1)



        // 키, 몸무게, 기초대사량, 목표설정 보이기


        //val a: String = (655.1+(9.56*wei)+(1.85*hei)-(4.68*25)).toString()
        //rate.setText(a)

        // 신체 정보 변경 버튼 클릭 > 편집 dialog 띄우기 > 변경 반영
        editBtn_body.setOnClickListener{
            var builder = AlertDialog.Builder(this)
            builder.setTitle("신체 정보 변경")

            var editView1 = layoutInflater.inflate(R.layout.dialog_body, null)
            builder.setView(editView1)
            builder.show()
/*
            editText_weight.findViewById<EditText>(R.id.editText_weight)
            editText_height.findViewById<EditText>(R.id.editText_height)
            btn_editBody.findViewById<Button>(R.id.btn_editBody)*/

            // DB 불러오기(키, 몸무게)


        }




        // 목표 변경 버튼 클릭 > SharedPreferences 사용해 앱 내 폴더에 목표만 저장
        editBtn_goal.setOnClickListener{
            showAddGoal()
        }



        // 알림 수정: 알림 클릭 시 편집

        // 알림 변경 버튼 클릭: 알림 추가
        editBtn_alert.setOnClickListener{

            var builder2 = AlertDialog.Builder(this)
            builder2.setTitle("신체 정보 변경")

            var editView2 = layoutInflater.inflate(R.layout.dialog_alert, null)
            builder2.setView(editView2)
            builder2.show()
            //var str_name:String =


            //sqlDB = dbManager.writableDatabase

/*
            btn_removeAlert.setOnClickListener{
                //sqlDB.execSQL("DELETE FROM alertDB WHERE name = '"+aName+"';")
            }
            btn_editalert.setOnClickListener{
                //sqlDB.execSQL("INSERT INTO alertDB VALUES ('"+aName+"'))
            }
*/
        }

        // 하단 네비게이션 이동
        bottomNavi.selectedItemId = R.id.action_mypage

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
                R.id.action_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("name", userName)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_mypage -> return@OnNavigationItemSelectedListener true
            }
            false
        })
    }
/*
    // 알람 DB 설정
    inner class myDBHelper(context: Context) : SQLiteOpenHelper(context, "alert", null, 1) {
        override fun onCreate(db: SQLiteDatabase?) { // 알람 이름, 시, 분, 실행 여부
            db!!.execSQL("CREATE TABLE alertDB " +
                    "(aName text, aHTime Integer, aMTime Integer, aDo bool);")
        }
        override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        }
    }*/

    //목표 변경 다이얼로그
    fun showAddGoal(){
        val goalDialogView = layoutInflater.inflate(R.layout.dialog_goal,null)
        val edt_goal : EditText by lazy {
            goalDialogView.findViewById(R.id.editText_goal)
        }
        val goal : TextView by lazy {
            findViewById(R.id.goal)
        }

        var builder = AlertDialog.Builder(this)
        val dialog = builder.setView(goalDialogView)
            .setPositiveButton("적용하기"){
                dialogInterface, i ->
            goal.setText(edt_goal.text) }
            .create()
        dialog.show()
    }
}