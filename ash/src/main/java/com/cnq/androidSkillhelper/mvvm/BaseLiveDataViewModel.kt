package com.cnq.androidSkillhelper.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.cnq.androidSkillhelper.manager.Toast
import com.cnq.androidSkillhelper.ui.dialog.Loading

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
    companion object {
       const val CLOSE_LOADING = "LEON1992"
    }

    var mDataSource: DS







    val showDialog: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }


    init {
        mDataSource = this.initDataSource()
    }


    //返回了datasource
    public abstract fun initDataSource(): DS

    protected fun loading(string: String?) {
        showDialog.value = string
    }

    protected fun loading() {
        showDialog.value = null
    }

    protected fun closeLoading() {
        showDialog.value = CLOSE_LOADING

    }


    class Factory<VM : ViewModel>(private val viewModel: VM) : NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return viewModel as T
        }


    }

}


