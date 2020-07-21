package com.cnq.androidskillhelper.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.cnq.androidSkillhelper.R
import kotlinx.android.synthetic.main.widget_dialog_loading.*

class LoadingDialog(context: Context) : Dialog(context, R.style.MDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widget_dialog_loading)
        setCanceledOnTouchOutside(false)
    }
    fun setContnent(message:Any?){
        loading_content.text = "sdffff"
    }
}