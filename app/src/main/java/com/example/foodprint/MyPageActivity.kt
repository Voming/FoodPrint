package com.example.foodprint

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_my_page.view.*
import java.sql.Time

class MyPageActivity : AppCompatActivity() {

    // 연결
    lateinit var editBtn_body: ImageButton
    lateinit var height: TextView
    lateinit var weight: TextView
    lateinit var rate: TextView
    lateinit var editBtn_goal: ImageButton
    lateinit var editBtn_alert: ImageButton
    lateinit var editText_weight: EditText //mypage 신체 정보 변경시 dialog의 edtiText
    lateinit var editText_height: EditText

    lateinit var layout: LinearLayout //알림 리스트가 보여질 레이아웃

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // 하단 네이게이션
        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView
        // 연결
        editBtn_body = findViewById(R.id.editBtn_body)

        rate = findViewById(R.id.rate)
        editBtn_goal = findViewById(R.id.editBtn_goal)
        editBtn_alert = findViewById(R.id.editBtn_alert)

        // 신체 정보 편집 연결
        //height = findViewById(R.id.height)
        //weight = findViewById(R.id.weight)
        //editText_weight = findViewById(R.id.editText_weight)
        //editText_height = findViewById(R.id.editText_height)

        //기초대사량
        //val a: String = (655.1+(9.56*weight)+(1.85*height)-(4.68*25)).toString()
        //rate.setText(a)

        // 신체 정보 변경 버튼 클릭 > 편집 dialog 띄우기 > 변경 반영
        editBtn_body.setOnClickListener{
            var builder = AlertDialog.Builder(this)
            /*val editW : EditText by lazy {
                findViewById(R.id.editText_weight)
            }
            val we : TextView by lazy {
                findViewById(R.id.weight)
            }*/
            //var h = editText_height.toString()
            builder.setTitle("신체 정보 변경")
                .setPositiveButton("적용하기",DialogInterface.OnClickListener{
                    dialog, id-> weight.text= "168"
                    //editText_weight = findViewById(R.id.editText_weight)
                    //editText_height = findViewById(R.id.editText_height)
                    //we.setText(editText_weight.text)
                })

            var editView1 = layoutInflater.inflate(R.layout.dialog_body, null)
            builder.setView(editView1)
            builder.show()

/*
            editText_weight.findViewById<EditText>(R.id.editText_weight)
            editText_height.findViewById<EditText>(R.id.editText_height)
            btn_editBody.findViewById<Button>(R.id.btn_editBody)*/
        }

        // 목표 변경 버튼 클릭 > SharedPreferences 사용해 앱 내 폴더에 목표만 저장
        editBtn_goal.setOnClickListener{
            showAddGoal()
        }

        // 기본 설정된 알람
        /*
        layout = findViewById(R.id.alertList)
        var alertList = ArrayList<Alr>()
        alertList.add(Alr("아침",6,20))
        alertList.add(Alr("점심",12,30))
        alertList.add(Alr("저녁",18,15))


        //update_alert(alertList) // 알람 리스트 갱신
        var num: Int =0
        // 알람 리스트로 activity에 추가
        for(i in alertList)
        {
            var str_name = i.name
            var str_hour = i.hour
            var str_min = i.min

            var layout_items: LinearLayout = LinearLayout(this)
            layout_items.orientation = LinearLayout.HORIZONTAL
            layout_items.id = num
            layout_items.setTag(str_name)

            layout.setPadding(40,20,40,10)

            var tvName: TextView = TextView(this)
            tvName.text = str_name
            tvName.textSize = 18f
            layout_items.addView(tvName)

            var tvHour: TextView = TextView(this)
            tvHour.text = "                                        "+str_hour.toString()+" : "+str_min.toString()
            tvHour.textSize = 18f
            layout_items.addView(tvHour)

            layout.addView(layout_items)
            num++

        }

         */

        // 알림 변경 버튼 클릭: 알림 추가
        editBtn_alert.setOnClickListener{

            var builder2 = AlertDialog.Builder(this)
            builder2.setTitle("신체 정보 변경")


            val dialogAlertView = layoutInflater.inflate(R.layout.dialog_alert,null)
            val a_name : EditText by lazy {
                dialogAlertView.findViewById(R.id.alertName)
            }
            val a_hour : TimePicker by lazy {
                dialogAlertView.findViewById(R.id.alertTime)
            }

            var editView2 = layoutInflater.inflate(R.layout.dialog_alert, null)
            builder2.setView(editView2)
                .setPositiveButton("적용하기"){
                        dialogInterface, i ->
                    //var n = a_name.getText().toString()
                    var h = a_hour.hour
                    var m = a_hour.minute
                    //alertList.add(Alr(a_name.text.toString(),h, m))
                    //update_alert(Alr(n, h, m),num++)
                }
            builder2.show()

        }
//
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

    // 알람 변경시 리스트에 추가
    fun update_alert(al: Alr, num: Int){
        var str_name = al.name
        var str_hour = al.hour
        var str_min = al.min
        var numb :Int = num

        var layout_items: LinearLayout = LinearLayout(this)
        layout_items.orientation = LinearLayout.HORIZONTAL
        layout_items.id = numb
        layout_items.setTag(str_name)

        layout.setPadding(40,20,40,10)

        var tvName: TextView = TextView(this)
        tvName.text = str_name
        tvName.textSize = 18f
        layout_items.addView(tvName)

        var tvHour: TextView = TextView(this)
        tvHour.text = "                                        "+str_hour.toString()+" : "+str_min.toString()
        tvHour.textSize = 18f
        layout_items.addView(tvHour)

        layout.addView(layout_items)
    }

}

// 알림 출력을 위한 클래스
class Alr(val name: String, val hour: Int, val min: Int) { }