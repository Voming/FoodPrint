package com.example.foodprint

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.item_alert.*
import java.util.*

class MyPageActivity : AppCompatActivity(), TimePicker.OnTimeChangedListener {

    // 연결
    lateinit var editBtn_body: ImageButton
    lateinit var editBtn_goal: ImageButton
    lateinit var editBtn_alert: ImageButton
    lateinit var height: TextView
    lateinit var weight: TextView
    lateinit var rate: TextView
    lateinit var aSwitch: Switch
    lateinit var btn_removeAlert: Button
    lateinit var btn_editalert: Button
    lateinit var alertList: LinearLayout
    lateinit var alertName: EditText
    lateinit var alertTime: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // 하단 네이게이션
        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView
        // 연결
        editBtn_body = findViewById(R.id.editBtn_body)
        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        rate = findViewById(R.id.rate)
        editBtn_goal = findViewById(R.id.editBtn_goal)
        editBtn_alert = findViewById(R.id.editBtn_alert)
        //alertList = findViewById(R.id.alertList)
        //alertName = findViewById(R.id.alertName)
        alertTime = findViewById(R.id.alertTime)


        // 신체 정보 변경 버튼 클릭 > 편집 dialog 띄우기 > 변경 반영
        editBtn_body.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("신체 정보 변경")

            var editView1 = layoutInflater.inflate(R.layout.dialog_body, null)
            builder.setView(editView1)
            builder.show()

        }


        //dialog의 timePicker
        alertTime = findViewById(R.id.alertTime)
        alertTime.setOnTimeChangedListener(this)

        // 알림 편집 Dialog 띄우기
        editBtn_alert.setOnClickListener {

            var builder2 = AlertDialog.Builder(this)
            builder2.setTitle("신체 정보 변경")
                .setPositiveButton("확인"){ _, _ ->  }

            var editView2 = layoutInflater.inflate(R.layout.dialog_alert, null)
            builder2.setView(editView2)
            builder2.show()
        }

        // 하단 네비게이션 이동
        bottomNavi.selectedItemId = R.id.action_mypage
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
                R.id.action_search -> {
                    startActivity(Intent(applicationContext, SearchActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_mypage -> return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    // TimePicker에서 시간이 변경될 때 마다 값을 받아 리스트에 저장
    override fun onTimeChanged(p0: TimePicker?, p1: Int, p2: Int) {
        // alertH.text = alertName.toString() //여기서 얻은 내용을 다 리스트에 저장


    }


}