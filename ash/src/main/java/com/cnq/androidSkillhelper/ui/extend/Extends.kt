package com.cnq.androidSkillhelper.ui.extend

import com.cnq.androidSkillhelper.mvvm.AbstractApplication

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/7/22
 * ============================
 **/

fun Float.dp2sp(): Float {
    val px = this.dp2px()
    return px.px2sp()
}

fun Float.dp2px(): Int {
    val density = AbstractApplication.context!!.resources.displayMetrics.density
    return (this * density).toInt()
}
fun Int.dp2px(): Int {
    val density = AbstractApplication.context!!.resources.displayMetrics.density
    return (this * density).toInt()
}

fun Int.sp2px(): Float {
    val fontScale = AbstractApplication.context!!.resources.displayMetrics.scaledDensity
    return (this.toFloat() * fontScale + 0.5f)
}

//  将px值转换为sp值，保证文字大小不变
fun Int.px2sp(): Float {
    val fontScale = AbstractApplication.context!!.resources.displayMetrics.scaledDensity
    return (this / fontScale + 0.5f)
}

class Extends {
}