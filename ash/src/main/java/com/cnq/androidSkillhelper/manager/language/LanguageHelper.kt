package com.pointsme.orderipadproject.multiLanguage

import android.app.Activity
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.cnq.androidSkillhelper.R
import com.cnq.androidSkillhelper.manager.SPUtils
import com.cnq.androidSkillhelper.manager.language.AppLanguageUtils

import java.util.*

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2019/9/26
 * ============================
 **/
object LanguageHelper {
    const val Italian="it"
    const val English="en"
    const val Chinese="zh"
    const val French="fr"
    const val Spanish="es"
    const val German="de"
    fun showLanguage(iconRes: Int, context: Context, datalist: List<String>, onLanguageCallback: OnLanguageCallback) {

        MaterialDialog(context).show {
            icon(iconRes)
            title(R.string.language)
            listItems(items = datalist) { _, index, _ ->
                onLanguageCallback.success(index)

            }
        }
    }

    interface OnLanguageCallback {
        public fun success(index: Int)
    }

    /**
     * 保存设置的语言
     */
    fun saveLanguage(mContext: Context, newLanguage: String) {
      //  SPUtils.put(mContext, SPUtils.LANGUAGE, newLanguage)
    }

    fun getSavedLanguage(mContext: Context): String {
       // return SPUtils.get(mContext, SPUtils.LANGUAGE, "it") as String
        return ""
    }

    fun getSavedLanguageLocale(mContext: Context): Locale {
        if (getSavedLanguage(mContext)==Italian){
            return Locale.ITALY
        }else if (getSavedLanguage(mContext)==English){
            return Locale.ENGLISH
        } else if (getSavedLanguage(mContext)==Chinese){
            return Locale.SIMPLIFIED_CHINESE
        }else if (getSavedLanguage(mContext)==French){
            return Locale.FRENCH
        }else if (getSavedLanguage(mContext)==Spanish){
            return Locale("es", "ES")
        }else if (getSavedLanguage(mContext)==German){
            return Locale.GERMAN
        }
        return Locale.getDefault()
    }

    fun onChangeAppLanguage(newLanguage: String, mContext: Activity) {
        val oldLanguage = getSavedLanguage(mContext)
        if (newLanguage == oldLanguage) {
            return
        }
        saveLanguage(mContext, newLanguage)
        AppLanguageUtils.changeAppLanguage(mContext, newLanguage)
        mContext.recreate()
    }
}