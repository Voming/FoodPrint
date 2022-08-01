package com.example.foodprint

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodprint.view.ToEatListAdapter
import com.example.foodprint.viewmodel.ToEatViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),CustomDialogInterface {

    val TAG: String = MainActivity::class.java.name;

    //ToEatViewModel 인스턴스를 만들고, 이를 관찰
    private lateinit var toeatViewModel: ToEatViewModel

    private lateinit var toeatdone:CheckBox

    private lateinit var toeatListAdapter: ToEatListAdapter
    private val toeatItems: ArrayList<ToEatModel> = ArrayList()

    private val recyclerview_list: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerview_list)
    }

    private val btn_add: ImageButton by lazy {
        findViewById<ImageButton>(R.id.addbtn)
    }



    lateinit var stickerbtn:ImageButton
    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val userName = intent.extras!!.getString("name")

        //RecyclerView 커스텀 달력이 수평으로 스크롤 되도록 LayoutManager 설정
        val monthListManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val monthListAdapter = AdapterMonth()


        stickerbtn=findViewById(R.id.sticker)

        //웃는 아이콘이 그려진 이미지 버튼 클릭 시, 식사 만족도를 고를 수 있는 CustomDialog가 팝업
        stickerbtn.setOnClickListener{
            val CustomDialog=CustomDialog(this,this)
            CustomDialog.show()
        }


        calendar_custom.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            scrollToPosition(Int.MAX_VALUE/2)
        }

        //달력의 한 달 씩 스크롤 되도록 설정
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(calendar_custom)

        var bottomNavi = findViewById(R.id.bottom_navigation) as BottomNavigationView

        // Set Home selected

        // Set Home selected
        bottomNavi.selectedItemId = R.id.action_calendar

        // Perform item selected listener

        // Perform item selected listener
        bottomNavi.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_calendar -> return@OnNavigationItemSelectedListener true
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

        initViewModel()
        initRecyclerview()
        initBtnAdd()
    }

    //매우만족 버튼 클릭 시 Object변수 값 1로 설정
    //num값은 외부 클래스인 AdapterDay에서도 활용되기 때문에 Object 변수로 선언
    //달력 클릭 시 이 Object변수 값에 따라 다른 스티커를 붙여준다.
    override fun onBtn1clicked() {
        Iconnum.num=1
    }

    //만족 버튼 클릭 시 Object변수 값 1로 설정
    override fun onBtn2clicked() {
        Iconnum.num=2
    }

    //보통 버튼 클릭 시 Object변수 값 1로 설정
    override fun onBtn3clicked() {
        Iconnum.num=3
    }

    //아쉽 버튼 클릭 시 Object변수 값 1로 설정
    override fun onBtn4clicked() {
        Iconnum.num=4
    }

    //매우아쉽 버튼 클릭 시 Object변수 값 1로 설정
    override fun onBtn5clicked() {
        Iconnum.num=5
    }


    //ViewModel 설정
    //View 에서 ViewModel 을 관찰하여 데이터가 변경될 때 내부적으로 자동으로 알 수 있도록 한다.
    //ViewModel 은 View 와 ViewModel 의 분리로 액티비티가 destroy 되었다가 다시 create 되어도 종료되지 않고 가지고 있다.
    private fun initViewModel() {
        toeatViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(ToEatViewModel::class.java)
        toeatViewModel.getTodoListAll().observe(this, androidx.lifecycle.Observer {
            toeatListAdapter.setTodoItems(it)
        })
    }


    //Recyclerview 설정
    //Recyclerview adapter 와 LinearLayoutManager 를 만들고 연결
    private fun initRecyclerview() {
        toeatListAdapter =
                //adapter 에 click 시 식단 계획표 (toeat) -> Unit 파라미터로 넘겨준다
            ToEatListAdapter({ toeat -> deleteDialog(toeat) })
        recyclerview_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = toeatListAdapter
        }
    }




    //btn_add 설정-> 버튼 클릭 시 식단 계획을 추가 할 수 있는 버튼을 띄우는 함수로 연결
    private fun initBtnAdd() {
        btn_add.setOnClickListener {
            showAddToeatDialog()
        }
    }



    //Toeat 리스트를 추가하는 Dialog 띄우기
    //ToeatModel 을 생성하여 리스트 add 해서 리스트를 갱신
    private fun showAddToeatDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add, null)
        val et_add_title: EditText by lazy {
            dialogView.findViewById<EditText>(R.id.et_add_title)
        }
        val et_add_content: EditText by lazy {
            dialogView.findViewById<EditText>(R.id.et_add_content)
        }
        var builder = AlertDialog.Builder(this)
        val dialog = builder.setView(dialogView)
            .setPositiveButton(
                "확인"
            ) { dialogInterface, i ->
                var id: Long? = null
                val title = et_add_title.text.toString()
                val content = et_add_content.text.toString()
                val createdDate = Date().time
                val todoModel = ToEatModel(
                    id,
                    toeatListAdapter.getItemCount() + 1,
                    title,
                    content,
                    createdDate
                )
                toeatViewModel.insert(todoModel)
            }
            .setNegativeButton("취소", null)
            .create()
        dialog.show()
    }


    //식단 리스트를 삭제하는 Dialog 띄우기
    private fun deleteDialog(todoModel: ToEatModel) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("선택한 식단을 삭제할까요? ")
            .setNegativeButton("취소") { _, _ -> }
            .setPositiveButton("확인") { _, _ ->
                toeatViewModel.delete(todoModel)
            }
            .create()
        builder.show()
    }

}