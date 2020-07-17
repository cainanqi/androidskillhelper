package com.pointsme.basemodule.manager

import android.app.Activity
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.cnq.androidSkillhelper.R

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2019/9/30
 * ============================
 **/
object MaterialDialogManager {

    fun showMessage(context: Context, message: String) {
        showMessage(context, context.getString(R.string.hint), message)
    }

    fun showMessage(context: Context, title: String, message: String) {
        MaterialDialog(context).show {
            title(text = title)
            message(text = message)
        }
    }


    fun showWithButton(activity: Activity?, content: String, successCallback: SuccessCallback?) {
        if (activity != null && !activity.isFinishing){
            MaterialDialog(activity).show {
                title(text = context.getString(R.string.hint))
                message(text = content)
                positiveButton(text = context.getString(R.string.sure)) {
                    successCallback?.clickSuccess()
                }
            }
        }
    }

    fun showWithButton(context: Context, title: String, content: String, successCallback: SuccessCallback?) {

        MaterialDialog(context).show {
            title(text = title)
            message(text = content)
            positiveButton(text = context.getString(R.string.sure)) {
                successCallback?.clickSuccess()
            }
            negativeButton(text = context.getString(R.string.cancel))
        }
    }


    fun showWithButton(context: Activity, content: String, positive: String, nagative: String, successCallback: SuccessCallback?) {
        if (context.isFinishing) {
            return
        }
        MaterialDialog(context).show {
            title(text = context.getString(R.string.hint))
            message(text = content)
            positiveButton(text = positive) {
                successCallback?.clickSuccess()
            }
            negativeButton(text = nagative)
        }
    }
    fun showWithButton(context: Activity, tiele: String,content: String, positive: String, nagative: String, successCallback: SuccessCallback?) {
        if (context.isFinishing) {
            return
        }
        MaterialDialog(context).show {
            title(text = tiele)
            message(text = content)
            positiveButton(text = positive) {

                successCallback?.clickSuccess()
            }
            negativeButton(text = nagative)
            cancelable(false)
        }
    }
    fun showWithButtonWithNoCancel(context: Activity, tiele: String,content: String, positive: String, successCallback: SuccessCallback?) {
        if (context.isFinishing) {
            return
        }
        MaterialDialog(context).show {
            title(text = tiele)
            message(text = content)
            positiveButton(text = positive) {

                successCallback?.clickSuccess()
            }
            cancelable(false)
        }
    }


    interface SuccessCallback {
        fun clickSuccess()
    }

    interface Success {
        fun success(index: Int)
    }














}