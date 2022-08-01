package com.example.foodprint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val intent = intent
        val userName = intent.extras!!.getString("name")

        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView

        // Set Home selected

        // Set Home selected
        bottomNavi.selectedItemId = R.id.action_recipe

        // Perform item selected listener

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
                R.id.action_recipe -> return@OnNavigationItemSelectedListener true
                R.id.action_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("name", userName)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
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