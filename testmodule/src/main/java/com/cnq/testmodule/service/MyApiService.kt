package com.cnq.testmodule.service

import com.cnq.androidSkillhelper.net.retrofit.ApiService
import com.cnq.androidSkillhelper.net.retrofit.Result
import com.cnq.testmodule.MarkBean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/28
 * ============================
 **/
 interface MyApiService {

    /**
     * 商家记录
     *
     * @param url
     * @return
     */
    @POST
    fun ShoperMark(@Url url: String?, @Body map: MarkBean?): Observable<Result<Any?>?>?
}