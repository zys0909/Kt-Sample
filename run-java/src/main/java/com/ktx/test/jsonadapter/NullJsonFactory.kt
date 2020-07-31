package com.ktx.test.jsonadapter

import com.squareup.moshi.*
import java.lang.reflect.Type

/**
 * 描述: Boolean，Double，Float，Int，Integer，Long，String
 *      List，Set
 * author zys
 * create by 2020/7/30
 */
object NullJsonFactory {

    /**
     * 是否兼容接口数据类型错误
     */
    var allowServiceError = false

    private val BOOLEAN_JSON_ADAPTER = NullBooleanAdapter()
    private val DOUBLE_JSON_ADAPTER = NullDoubleAdapter()
    private val FLOAT_JSON_ADAPTER = NullFloatAdapter()
    private val INTEGER_JSON_ADAPTER = NullIntAdapter()
    private val LONG_JSON_ADAPTER = NullLongAdapter()
    private val STRING_JSON_ADAPTER = NullStringAdapter()

    val STANDARD = JsonAdapter.Factory { type, _, _ ->
        when (type) {
            Boolean::class.java -> BOOLEAN_JSON_ADAPTER
            Double::class.java -> DOUBLE_JSON_ADAPTER
            Float::class.java -> FLOAT_JSON_ADAPTER
            Int::class.java, Integer::class.java -> INTEGER_JSON_ADAPTER
            Long::class.java -> LONG_JSON_ADAPTER
            String::class.java -> STRING_JSON_ADAPTER
            else -> null
        }
    }

    val COLLECTION = JsonAdapter.Factory { type, annotations, moShi ->
        val rawType = Types.getRawType(type)
        when {
            annotations.isNotEmpty() -> null
            rawType == List::class.java -> createListAdapter(type, moShi)
            rawType == Set::class.java -> createSetAdapter(type, moShi)
            else -> null
        }
    }

    private fun createListAdapter(type: Type, moShi: Moshi): JsonAdapter<*> {
        val elementType = Types.collectionElementType(type, MutableCollection::class.java)
        val elementAdapter: JsonAdapter<Any> = moShi.adapter(elementType)
        return NullListAdapter(elementAdapter)
    }

    private fun createSetAdapter(type: Type, moShi: Moshi): JsonAdapter<*> {
        val elementType = Types.collectionElementType(type, MutableCollection::class.java)
        val elementAdapter: JsonAdapter<Any> = moShi.adapter(elementType)
        return NullSetAdapter(elementAdapter)
    }
}