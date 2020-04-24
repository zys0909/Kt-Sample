package com.dev.zhaoys.ui.proxy;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020-01-06
 */
public class DynamicJava {

    public static void t() {
        Object o = new DynamicJava().newProxyInstance(new BuyImpl());
        if (o instanceof IBuy) {
            ((IBuy) o).buyTicket("DynamicJava", -10);
        }
    }

    private Object object;

    public Object newProxyInstance(Object o) {
        this.object = o;
        return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), this::invoke);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Log.i("测试TAG", "invoke before");
        Log.i("测试TAG", "proxy -> " + proxy.getClass().getName());
        Object o = method.invoke(object, args);
        Log.i("测试TAG", "invoke after");
        return o;
    }
}
