package com.example.foodprint

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText

class BodyActivity (context: Context){
    lateinit var editText_weight: EditText
    lateinit var editText_height: EditText
    lateinit var btn_editBody: Button
    val dialog = Dialog(context)

    /*private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }*/

    /*fun showDialog()
    {
        dialog.setContentView(R.layout.dialog_body)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val edit_name = dialog.findViewById<EditText>(R.id.name_edit)

        dialog.cancel_button.setOnClickListener {
            dialog.dismiss()
        }

        dialog.btn_editBody.setOnClickListener {
            onClickListener.onClicked(edit_name.text.toString())
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }*/

}

    // 기초대사량 계산
    /*btn_editBody.setOnClickListener {
        var weight_ed = editText_weight.toString()
        var height_ed = editText_weight.toString()

        height.setText(height_ed)
        weight.setText(weight_ed)

        var wei: Int = Integer.parseInt(weight_ed)
        var hei: Int = Integer.parseInt(height_ed)

        val a: String = (655.1+(9.56*wei)+(1.85*hei)-(4.68*25)).toString()
        rate.setText(a)8
    }*/


