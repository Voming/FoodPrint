package com.example.foodprint

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.calendar_dialog.*

class CustomDialog(context: Context,
                   CustomDialogInterface : CustomDialogInterface)
    : Dialog(context), View.OnClickListener {

    private var CustomDialogInterface : CustomDialogInterface? = null

    //인터페이스 연결
    init{
        this.CustomDialogInterface=CustomDialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar_dialog)

        //이미지 버튼에 클릭 이벤트 적용
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view){
            //MainActivity에서도 클릭된 사실이 감지되도록 인터페이스를 활용
            btn1->{
                this.CustomDialogInterface?.onBtn1clicked()
                dismiss()
            }
            btn2->{
                this.CustomDialogInterface?.onBtn2clicked()
                dismiss()
            }
            btn3->{
                this.CustomDialogInterface?.onBtn3clicked()
                dismiss()
            }
            btn4->{
                this.CustomDialogInterface?.onBtn4clicked()
                dismiss()
            }
            btn5->{
                this.CustomDialogInterface?.onBtn5clicked()
                dismiss()
            }
        }
    }


}