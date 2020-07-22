package com.cnq.androidSkillhelper.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.cnq.androidSkillhelper.R
import com.cnq.androidSkillhelper.mvvm.AbstractApplication
import kotlinx.android.synthetic.main.widget_dialog_loading.*

class Loading(context: Context) : Dialog(context, R.style.MDialog) {

    companion object {
        private lateinit var context: Context

        private var instance: Loading? = null
            get() {
                if (field == null) {
                    instance = Loading(context)
                }
                return field
            }

        fun show(context: Context): Companion {
            this.context = context
            instance?.show()
            return this

        }

        fun setContnent(message: Any?): Companion {
            if (instance != null) {
                message?.let { instance!!.setContnent(it) }
            }
            return this
        }

        fun cancel() {
            if (instance != null) {
                instance!!.cancel()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widget_dialog_loading)
        setCanceledOnTouchOutside(false)
    }

    private fun setContnent(message: Any) {
        loading_content.text = message.toString()
    }
}