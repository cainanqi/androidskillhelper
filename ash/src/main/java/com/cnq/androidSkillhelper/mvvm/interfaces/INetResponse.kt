package com.cnq.androidSkillhelper.mvvm.interfaces

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/7/24
 * ============================
 **/
interface INetResponse<T> {

    fun code(): Int
    fun msg(): String
    fun data(): T
    fun isSuccess(): Boolean
}