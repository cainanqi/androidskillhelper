package com.cnq.androidskillhelper.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory

/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/5/26
 * ============================
 **/
@Suppress("UNCHECKED_CAST")
abstract class BaseLiveDataViewModel<DS : BaseDataSource> : ViewModel() {
    protected var mDataSource: DS


    private val showDialog: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    init {
        mDataSource = this.initDataSource()
    }


    //返回了datasource
    public abstract fun initDataSource(): DS

    fun showLoading(string: String) {
        showDialog.value = string
    }

    fun getLoading(): MutableLiveData<String> {
        return showDialog
    }


    class Factory<VM : ViewModel>(private val viewModel: VM) : NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return viewModel as T
        }


    }

}


