package com.cnq.androidskillhelper.manager

import android.util.Log

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020-4-29
 * ============================
 **/
class LogPrinter {
    companion object {
        var logPrinter: LogPrinter? = null
       private var openOrClose1 :Boolean=true

        fun instance(): LogPrinter {

            if (logPrinter == null) {
                logPrinter = LogPrinter()

            }
            return logPrinter as LogPrinter
        }

        fun init(openOrClose :Boolean){
            openOrClose1 =openOrClose
        }
        fun allowLog():Boolean{
           return openOrClose1
        }

        fun i(tag:Any,i:Any){
            if (!openOrClose1){
                return
            }
            Log.i(tag.toString(),i.toString())
        }
        fun d(tag:Any,d:Any){
            if (!openOrClose1){
                return
            }
            Log.d(tag.toString(),d.toString())
        }
        fun e(tag:Any,e:Any){
            if (!openOrClose1){
                return
            }
            Log.d(tag.toString(),e.toString())
        }
    }
}