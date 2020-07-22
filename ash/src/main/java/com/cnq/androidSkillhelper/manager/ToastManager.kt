package com.cnq.androidSkillhelper.manager

import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import android.widget.Toast
import com.cnq.androidSkillhelper.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toast_view.view.*

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2019/11/15
 * ============================
 **/
object ToastManager {


    fun toastMessage(context: Context, message: Any?) {

        if (Thread.currentThread() != Looper.getMainLooper().thread) {
            Looper.prepare()
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.toast_view, null)



        val content: String = if (message == null) {
            ""
        } else {
            when (message) {
                is String -> message
                else -> message.toString()
            }
        }

        view.toastText.text = content
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_LONG//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.view = view //添加视图文件

        val anim=ScaleAnimation(0f,1f,0f,1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration=300
        toast.view?.startAnimation(anim)
        toast.show()
        if (Thread.currentThread() != Looper.getMainLooper().thread) {
            Looper.loop()
        }

    }

    fun showSnackBar(view:View,message: Any?){
        val content: String = if (message == null) {
            ""
        } else {
            when (message) {
                is String -> message
                else -> message.toString()
            }
        }

     val snackBar=   Snackbar.make(view, content, Snackbar.LENGTH_SHORT)
        val snackBarView = snackBar.view//获取Snackbar显示的View对象
        //获取显示文本View,并设置其显示颜色
        val messageText=snackBarView.findViewById(R.id.snackbar_text) as TextView
        messageText.setTextColor(Color.parseColor("#FFFFFF"))

      //设置SnackBar的背景色
        snackBarView.setBackgroundColor(Color.parseColor("#108ee9"))
        snackBar.show()

    }


}

