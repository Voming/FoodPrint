package com.example.foodprint

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_day.view.*
import kotlinx.android.synthetic.main.list_item_month.view.*
import java.util.*

class AdapterDay (val tempMonth:Int, val dayList: MutableList<Date>): RecyclerView.Adapter<AdapterDay.DayView>(){

    //달력 수직 항목의 개수 = 달력에 표시될 주 = 6주
    val ROW = 6

    inner class DayView(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)
        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {
        //각 날짜 항목이 클릭 되었을 때 num값에 따라 스티커 변경
        holder.layout.item_day_icon.setOnClickListener {

            //스티커 클릭으로 이미 변경된 값을 가지고 있는 num값을 기준으로 달력의 스티커를 변경
            //달력 스티커변경 후 num값은 0으로 초기화
            when (Iconnum.num) {
                1 -> {
                    holder.layout.item_day_icon.setImageResource(R.drawable.icon1)
                    Iconnum.num = 0
                }
                2 -> {
                    holder.layout.item_day_icon.setBackgroundResource(R.drawable.icon2)
                    Iconnum.num = 0
                }
                3 -> {
                    holder.layout.item_day_icon.setImageResource(R.drawable.icon3)
                    Iconnum.num = 0
                }
                4 -> {
                    holder.layout.item_day_icon.setImageResource(R.drawable.icon4)
                    Iconnum.num = 0
                }
                5 -> {
                    holder.layout.item_day_icon.setImageResource(R.drawable.icon5)
                    Iconnum.num = 0
                }
            }
        }
        holder.layout.item_day_text.text = dayList[position].date.toString()


        /*평일과 주말 색깔로 구분*/
        //일요일
        if(position % 7==0) {
            holder.layout.item_day_text.setTextColor(Color.RED)
            holder.layout.item_day_text.alpha = 0.4f
        //토요일
        }else if(position % 7==6) {
            holder.layout.item_day_text.setTextColor(Color.RED)
            holder.layout.item_day_text.alpha = 0.4f
        //평일
        }else{
            holder.layout.item_day_text.setTextColor(Color.BLACK)
        }

        //현재 월이 아닌 날짜는 투명도를 낮춰 희미하게 보이게 설정
        if(tempMonth != dayList[position].month) {
            holder.layout.item_day_text.alpha = 0.4f
            holder.layout.item_day_icon.setImageResource(R.drawable.empty_day)
        }

    }

    //6주 X 7일 총 42개의 날짜 표시
    override fun getItemCount(): Int {
        return ROW * 7
    }

}