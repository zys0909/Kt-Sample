package com.dev.zhaoys.ui.proxy

import android.util.Log
import androidx.annotation.NonNull
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * 描述:
 *
 * author zys
 * create by 2020-01-03
 */
object DynamicTest {

    fun t1() {
        val iBuy = DynamicProXy().newProxyInstance(BuyImpl())
        if (iBuy is IBuy) {
            iBuy.buyTicket("test", -100)
        } else {
            Log.i("测试TAG", iBuy.toString())
        }
    }


}

class DynamicProXy : InvocationHandler {
    lateinit var any: Any
    fun newProxyInstance(
        obj: Any
    ): Any {
        any = obj
        return Proxy.newProxyInstance(obj.javaClass.classLoader, obj.javaClass.interfaces, this)
    }


    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
        Log.i("测试TAG", "before invoke")
        val invoke = method.invoke(any, args)
        Log.i("测试TAG", "after invoke")
        return invoke
    }
}

class BuyImpl : IBuy {
    override fun buyTicket(s: String, i: Int) {
        Log.i("测试TAG", "BuyImpl $s ->$i")
    }
}

interface IBuy {
    fun buyTicket(@NonNull s: String, i: Int)
}