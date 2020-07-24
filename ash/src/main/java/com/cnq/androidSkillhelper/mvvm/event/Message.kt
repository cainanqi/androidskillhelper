package com.cnq.androidSkillhelper.mvvm.event

/**
 *   @auther : LEON X
 *   time   : 2019/11/13
 */
class Message @JvmOverloads constructor(
    var code: Int = 0,
    var msg: String = "",
    var arg1: Int = 0,
    var arg2: Int = 0,
    var obj: Any? = null
)