package com.cnq.androidSkillhelper.ui.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.cnq.androidSkillhelper.mvvm.AbstractApplication

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/7/23
 * ============================
 **/
object KeyboardUtil {
    fun showKeyboard(editText: EditText) {
        val imm =
            AbstractApplication.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    fun hideKeyboard(editText: EditText) {
        val imm1 =
            AbstractApplication.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm1.hideSoftInputFromWindow(editText.windowToken, 0)
    }

}