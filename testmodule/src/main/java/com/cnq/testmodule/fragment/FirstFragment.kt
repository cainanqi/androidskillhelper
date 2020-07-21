package com.cnq.testmodule.fragment

import android.util.Log
import androidx.lifecycle.Observer
import com.cnq.androidskillhelper.mvvm.BaseLiveDataFragment
import com.cnq.testmodule.FirstDataSource
import com.cnq.testmodule.R
import com.cnq.testmodule.databinding.FragmentFirstBinding
import com.cnq.testmodule.viewmodel.FirstViewModel
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : BaseLiveDataFragment<FragmentFirstBinding, FirstDataSource, FirstViewModel>() {
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
            mViewModel.onRefresh()

//            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }




    }


}
