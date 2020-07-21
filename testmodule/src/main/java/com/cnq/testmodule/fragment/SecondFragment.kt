package com.cnq.testmodule.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cnq.androidSkillhelper.mvvm.BaseLiveDataFragment
import com.cnq.androidSkillhelper.mvvm.BaseLiveDataViewModel
import com.cnq.testmodule.FirstDataSource

import com.cnq.testmodule.R
import com.cnq.testmodule.databinding.SecondFragmentBinding
import com.cnq.testmodule.service.MyApiService
import com.cnq.testmodule.viewmodel.FirstViewModel

class SecondFragment : BaseLiveDataFragment<SecondFragmentBinding,FirstDataSource,FirstViewModel>() {
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
