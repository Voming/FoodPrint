package com.example.foodprint

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    lateinit var etId: EditText
    lateinit var etPass: EditText
    lateinit var btnLogin: Button

    lateinit var dbmanger: userDBManager
    lateinit var sqLitedb: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //각 요소와 연결
        etId = findViewById(R.id.etId)
        etPass = findViewById(R.id.etPass)
        btnLogin = findViewById<Button>(R.id.btnLogin)

        //DB연결
        dbmanger = userDBManager(this, "userDB", null, 1)

        btnLogin.setOnClickListener {
            var str_id = etId.text.toString()
            var str_pass = etPass.text.toString()

            sqLitedb = dbmanger.readableDatabase

            var cursor: Cursor

            //아이디와 비밀번호를 비교하여 로그인절차
            cursor = sqLitedb.rawQuery("SELECT * FROM userDB WHERE id = '"+ str_id+"';", null)
            while(cursor.moveToNext()) {
                var search_pass = cursor.getString(cursor.getColumnIndex("pass")).toString()

                //DB에서 찾은 비밀번호가 입력한 것과 같으면 로그인 성공
                if(str_pass == search_pass)
                {
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                    //홈 페이지로 이동
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "로그인 실패 : 아이디와 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            cursor.close()
            sqLitedb.close()
            dbmanger.close()
        }

    }
}