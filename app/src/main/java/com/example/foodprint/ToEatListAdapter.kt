package com.example.foodprint.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodprint.R
import com.example.foodprint.ToEatModel
import java.text.SimpleDateFormat
import java.util.*


class ToEatListAdapter(val deletetItemClick: (ToEatModel) -> Unit) :
    RecyclerView.Adapter<ToEatListAdapter.ToeatViewHolder>() {
    private var toeatItems: List<ToEatModel> = listOf()

    /*
    * 이 어뎁터가 아이템을 얼마나 가지고 있는지 얻는 함수
    * */
    override fun getItemCount(): Int {

        Log.d("MainActivity", "todoItem getItemCount !!: " + toeatItems.size);
        return toeatItems.size
    }

    /*
    * 현재 아이템이 사용할 뷰홀더를 생성하여 반환하는 함수
    * item_list 레이아웃을 사용하여 뷰를 생성하고 뷰홀더에 뷰를 전달하여 생성된 뷰홀더를 반환
    * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToeatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        val viewHolder = ToeatViewHolder(view)
        return viewHolder
    }

    /*
    * 현재 아이템의 포지션에 대한 데이터 모델을 리스트에서 얻고
    * holder 객체를 ToeatViewHolder 로 형변환한 뒤 bind 메서드에 이 모델을 전달하여 데이터를 바인딩하도록 한다
    * */
    override fun onBindViewHolder(holder: ToeatViewHolder, position: Int) {
        val toeatModel = toeatItems[position]
        toeatModel.seq = position + 1;
        val todoViewHolder = holder as ToeatViewHolder
        todoViewHolder.bind(toeatModel)
    }

    /* 데이터베이스가 변경될 때마다 호출 */
    fun setTodoItems(toeatItems: List<ToEatModel>) {
        this.toeatItems = toeatItems
        Log.d("MainActivity", "todoItem setTodoItems !!: " + toeatItems.size);
        notifyDataSetChanged()
    }

    /*
    * 뷰홀더는 리스트를 스크롤하는 동안 뷰를 생성하고 다시 뷰의 구성요소를 찾는 행위를 반복하면서 생기는
    * 성능저하를 방지하기 위해 미리 저장 해 놓고 빠르게 접근하기 위하여 사용하는 객체
    * */
    inner class ToeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        private val tv_content = itemView.findViewById<TextView>(R.id.tv_content)
        private val tv_date = itemView.findViewById<TextView>(R.id.tv_date)
        private val iv_delete = itemView.findViewById<ImageView>(R.id.iv_delete)

        fun bind(toeatModel: ToEatModel) {

            tv_title.text = toeatModel.title
            tv_content.text = toeatModel.content
            tv_date.text = toeatModel.createDate.convertDateToString("yyyy.MM.dd HH:mm")

            iv_delete.setOnClickListener {
                deletetItemClick(toeatModel)
            }

        }

    }

}


/* createDate 을 Date to String  */
fun Long.convertDateToString(format: String): String {
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.format(Date(this))
}