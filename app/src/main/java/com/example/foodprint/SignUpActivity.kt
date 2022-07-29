package com.example.foodprint

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class SignUpActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etId: EditText
    lateinit var etPass: EditText
    lateinit var rgGender: RadioGroup
    lateinit var male: RadioButton
    lateinit var female: RadioButton
    lateinit var etHeight: EditText
    lateinit var etweight: EditText
    lateinit var btnSignUp: Button

    lateinit var dbmanger: userDBManager
    lateinit var sqLitedb: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //각 요소와 연결
        etName = findViewById(R.id.etName)
        etId = findViewById(R.id.etId)
        etPass = findViewById(R.id.etPass)
        rgGender = findViewById(R.id.rgGender)
        male = findViewById(R.id.male)
        female = findViewById(R.id.female)
        etHeight = findViewById(R.id.etHeight)
        etweight = findViewById(R.id.etweight)
        btnSignUp = findViewById<Button>(R.id.btnSignUp)

        //DB연결
        dbmanger = userDBManager(this, "userDB", null, 1)

        btnSignUp.setOnClickListener {
            var str_name = etName.text.toString()
            var str_id = etId.text.toString()
            var str_pass = etPass.text.toString()
            var int_height = etHeight.text.toString().toInt()
            var int_weight = etweight.text.toString().toInt()
            var str_gender: String = ""

            if (rgGender.checkedRadioButtonId == R.id.male) {
                str_gender = male.text.toString()
            }
            if (rgGender.checkedRadioButtonId == R.id.female) {
                str_gender = female.text.toString()
            }

            //사용자 정보 DB에 입력
            sqLitedb = dbmanger.writableDatabase
            sqLitedb.execSQL(
                "INSERT INTO userDB VALUES('" + str_name + "', '" + str_id + "', '" + str_pass + "', '" + str_gender + "', '" +
                        int_height + "', '" + int_weight + "');"
            )
            sqLitedb.close()
            dbmanger.close()

            //입력 성공하면 토스트 메시지
            Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()

            //처음 페이지로 이동
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }


    }

}
