package com.zys.common.net

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.requestCoroutineScope
import androidx.lifecycle.viewModelScope

/**
 * 描述:网络调用的封装
 *
 * author zys
 * create by 2021/1/14
 */

object Http {

    fun with(viewModel: ViewModel): RequestCoroutineScope {
        viewModel.viewModelScope
        return viewModel.requestCoroutineScope
    }

    fun with(activity: FragmentActivity): RequestCoroutineScope {
        activity.lifecycleScope
        return activity.lifecycle.requestCoroutineScope
    }

    fun with(fragment: Fragment): RequestCoroutineScope {
        return fragment.lifecycle.requestCoroutineScope
    }

}

const val ERROR_NET = 1000



