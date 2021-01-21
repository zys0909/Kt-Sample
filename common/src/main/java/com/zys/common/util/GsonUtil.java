package com.zys.common.util;


import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2021/1/20
 */
@SuppressWarnings({"SpellCheckingInspection","unused"})
public class GsonUtil {
    private final static Gson g = new Gson();

    private GsonUtil() {

    }

    public static <E> String toJson(E o) {
        if (o == null) {
            return "";
        }
        try {
            return g.toJson(o);
        } catch (Exception e) {
            return "";
        }
    }


    public static <E> List<E> parseArray(String json, Class<E> clazz) {
        ParameterizedType type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, clazz);
        return g.fromJson(json, type);
    }

    public static <T, V> T copyProperties(@NonNull Object old, Class<V> clazzOld, Class<T> clazzNew) {
        T t = null;
        try {
            String s = g.toJson(old, clazzOld);
            t = g.fromJson(s, clazzNew);
        } catch (Exception e) {
            //
        }
        return t;
    }
}
