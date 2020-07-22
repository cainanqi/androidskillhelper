package com.cnq.testmodule.fragment

import com.cnq.androidSkillhelper.mvvm.BaseLiveDataFragment
import com.cnq.testmodule.FirstDataSource

import com.cnq.testmodule.R
import com.cnq.testmodule.databinding.SecondFragmentBinding
import com.cnq.testmodule.viewmodel.FirstViewModel

class SecondFragment : BaseLiveDataFragment<SecondFragmentBinding, FirstDataSource, FirstViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.second_fragment
    }

    override fun initViewModel(): FirstViewModel {
        mViewModel= FirstViewModel()
        return mViewModel
    }

    override fun onBindingView() {
    }


}
