package com.cnq.androidSkillhelper.mvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cnq.androidSkillhelper.manager.Timer
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
abstract class BaseLiveDataFragment<VB : ViewDataBinding, DS : BaseDataSource, VM : BaseLiveDataViewModel<DS>> :
    Fragment() {
    private var rootView: View? = null
    protected lateinit var mBinding: VB

    protected lateinit var mViewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false)

        }
        return rootView
    }


    open fun loading(msg: Any?) {
        Loading.show(requireContext())
        Loading.setContnent(msg)

    }

    open fun loading() {
        Loading.show(requireContext())

    }

    open fun closeLoading() {
        Loading.cancel()
    }

    open fun toast(msg: Any?) {
        Toast.toast(requireContext(), msg)
    }

    //定时器
    protected open fun getTimer(times: Int, miles: Long, timerCallback: Timer.TimerCallback) {
        var times = times
        val t = Timer()
        if (times < 0) {
            times = Int.MAX_VALUE
        }
        t.start(times, miles, timerCallback)
        lifecycle.addObserver(t)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = DataBindingUtil.bind(view)!!
        val factory: BaseLiveDataViewModel.Factory<VM> =
            BaseLiveDataViewModel.Factory(initViewModel())
        mViewModel = ViewModelProvider(this, factory).get(initViewModel()::class.java)
        mBinding.lifecycleOwner = this
        viewLifecycleOwner.lifecycle.addObserver(mViewModel.initDataSource())
        initObserver()

        onBindingView()
    }

    private fun initObserver() {
        mViewModel.showDialog.observe(viewLifecycleOwner, Observer {
            when (it) {
                null -> {
                    loading()
                }
                BaseLiveDataViewModel.CLOSE_LOADING -> {
                    closeLoading()
                }
                else -> {
                    loading(it)
                }
            }
        })
        mViewModel.mDataSource.defUI.showDialog.observe(viewLifecycleOwner, Observer {
            loading()
        })
        mViewModel.mDataSource.defUI.dismissDialog.observe(viewLifecycleOwner, Observer {
            closeLoading()
        })
        mViewModel.mDataSource.defUI.toastEvent.observe(viewLifecycleOwner, Observer {
            toast(it)
        })
    }

    abstract fun getLayoutId(): Int

    /**
     * 创建ViewModel并设置与Binding关联
     *
     * @return ViewModel
     */
    protected abstract fun initViewModel(): VM

    protected abstract fun onBindingView()

    //fragment跳转
    protected fun navigate(destination: Int) {
        findNavController().navigate(destination)

    }

    protected fun navigate(destination: Int, bundle: Bundle) {
        findNavController().navigate(destination, bundle)

    }

}