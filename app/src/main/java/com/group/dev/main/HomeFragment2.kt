package com.group.dev.main

import android.view.View
import androidx.lifecycle.liveData
import com.group.common.adapter.ItemCell
import com.group.common.base.BaseSampleFragment
import com.group.common.ext.openActivity
import com.group.dev.ui.wanandroid.WanMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import kotlin.coroutines.coroutineContext

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/26
 */
class HomeFragment2 : BaseSampleFragment() {

    override fun init(view: View) {
        setTitle("BBBB", false)
    }

    override fun initList(): List<ItemCell> {
        val list = mutableListOf<MainCell>()
        list.add(MainCell("WanAndroid") {
            openActivity<WanMainActivity>()
        })
        list.add(MainCell("协程测试") {

        })
        return list
    }

    fun test(temp: String, block: String.() -> Unit) {

        block(temp)

        block.invoke(temp)

        temp.block()
    }


    private fun test() {
        val block: suspend () -> String = {
            Timber.tag("测试TAG").i("block -> %s,协程-%s", Thread.currentThread().name, coroutineContext)
            delay(3000)
            "执行异步操作"
        }
        liveData {
            flow {
                Timber.tag("测试TAG").i("flow -> %s,协程-%s", Thread.currentThread().name,
                    coroutineContext
                )
                emit(block.invoke())
            }.catch {
                Timber.tag("测试TAG").i("catch -> %s,协程-%s", Thread.currentThread().name,
                    kotlin.coroutines.coroutineContext
                )
                emit("出错了")
            }.flowOn(Dispatchers.IO).collect {
                Timber.tag("测试TAG").i("collect -> %s,协程-%s", Thread.currentThread().name, coroutineContext)
                emit(it)
            }
        }.observe(this) {
            Timber.tag("测试TAG").i("observe -> %s", Thread.currentThread().name)
            Timber.tag("测试TAG").i("test -> %s", it)
        }
    }
}