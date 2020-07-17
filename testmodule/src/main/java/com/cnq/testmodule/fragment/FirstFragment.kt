package com.cnq.testmodule.fragment

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cn.leancloud.AVInstallation
import cn.leancloud.push.PushService
import com.cnq.androidSkillhelper.mvvm.BaseLiveDataFragment
import com.cnq.testmodule.FirstDataSource
import com.cnq.testmodule.MainActivity
import com.cnq.testmodule.R
import com.cnq.testmodule.databinding.FragmentFirstBinding
import com.cnq.testmodule.service.MyApiService
import com.cnq.testmodule.viewmodel.FirstViewModel
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment :BaseLiveDataFragment<FragmentFirstBinding,MyApiService,FirstDataSource,FirstViewModel>() {
    override fun getLayoutId(): Int {
        return  R.layout.fragment_first
    }

    override fun initViewModel(): FirstViewModel {
        mViewModel= FirstViewModel()
        mBinding.model=mViewModel
        return mViewModel
    }

    override fun onBindingView() {
        val nameObserver=Observer<String>{
            Log.d("BaseDataSource", "接收数据")
            textView.text=it
        }
        mViewModel.currentTime1.observe(this,nameObserver)
        button.setOnClickListener{
//            Log.d("测试测试","一点击")
//            mViewModel.showLoading("2222")

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }




    }


}
