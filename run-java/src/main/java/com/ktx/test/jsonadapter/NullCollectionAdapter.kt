package com.ktx.test.jsonadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException

/**
 * 描述:集合类型
 *
 * author zys
 * create by 2020/7/30
 */
internal class NullListAdapter<T> constructor(private val elementAdapter: JsonAdapter<T>) :
    JsonAdapter<MutableList<T>>() {

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): MutableList<T>? {
        val result = mutableListOf<T>()
        if (reader.next(JsonReader.Token.BEGIN_ARRAY)) {
            return result
        }
        reader.beginArray()
        while (reader.hasNext()) {
            elementAdapter.fromJson(reader)?.run {
                result.add(this)
            }
        }
        reader.endArray()
        return result
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: MutableList<T>?) {
        if (value.isNullOrEmpty()) {
            writer.nullValue()
            return
        }
        writer.beginArray()
        value.run {
            for (element in value) {
                elementAdapter.toJson(writer, element)
            }
        }
        writer.endArray()
    }

}

internal class NullSetAdapter<T> constructor(private val elementAdapter: JsonAdapter<T>) :
    JsonAdapter<MutableSet<T>>() {

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): MutableSet<T>? {
        val result = mutableSetOf<T>()
        if (reader.next(JsonReader.Token.BEGIN_ARRAY)) {
            return result
        }
        reader.beginArray()
        while (reader.hasNext()) {
            elementAdapter.fromJson(reader)?.run {
                result.add(this)
            }
        }
        reader.endArray()
        return result
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: MutableSet<T>?) {
        if (value.isNullOrEmpty()) {
            writer.nullValue()
            return
        }
        writer.beginArray()
        value.run {
            for (element in value) {
                elementAdapter.toJson(writer, element)
            }
        }
        writer.endArray()
    }

}