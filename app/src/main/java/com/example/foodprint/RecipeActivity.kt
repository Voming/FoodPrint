package com.example.foodprint

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.timer

class RecipeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        /*val intent = intent
        val userName = intent.extras!!.getString("name")*/

        var pager= findViewById<ViewPager>(R.id.pager)
        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView



        // Set Home selected
        bottomNavi.selectedItemId = R.id.action_recipe

        // Perform item selected listener

        // Perform item selected listener
        bottomNavi.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_calendar -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_recipe -> return@OnNavigationItemSelectedListener true
                R.id.action_search -> {
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



        val fragments = ArrayList<Fragment>()

        //프래그먼트에 화면 추가하기
        fragments.add(Recipe())
        fragments.add(Recipe2())
        fragments.add(Recipe3())
        fragments.add(Recipe4())
        fragments.add(Recipe5())

        //어댑터와 연결
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        pager.adapter = adapter



        //3초마다 레시피 5개 자동 슬라이드
        timer(period = 3000){
            runOnUiThread {
                if(pager.currentItem < adapter.count-1 ){
                    pager.currentItem = pager.currentItem+1
                }
                else{
                    pager.currentItem =0
                }
            }
        }

        //이전 레시피 버튼 클릭 시 화면 전환
        val previousBtn1 = findViewById<ImageButton>(R.id.prevoisBtn)
        previousBtn1.setOnClickListener {
            pager.setCurrentItem(pager.currentItem-1,true)

        }
        //다음 레시피 버튼 클릭 시 화면 전환
        val nextBtn1 = findViewById<ImageButton>(R.id.nextBtn)
        nextBtn1.setOnClickListener {
            pager.setCurrentItem(pager.currentItem+1,true)
        }




    }

}