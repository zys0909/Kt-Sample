package com.ktx.test.jsonadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException

/**
 * 描述:基础类型
 *
 * author zys
 * create by 2020/7/30
 */
private const val BOOLEAN_DEFAULT = false
private const val DOUBLE_DEFAULT = 0.0
private const val FLOAT_DEFAULT = 0f
private const val INTEGER_DEFAULT = 0
private const val LONG_DEFAULT = 0L
private const val STRING_DEFAULT = ""

internal class NullBooleanAdapter : JsonAdapter<Boolean>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Boolean {
        if (reader.next(JsonReader.Token.BOOLEAN)) {
            return BOOLEAN_DEFAULT
        }
        return reader.nextBoolean()
    }

    @Throws(IOException::class)
    override fun toJson(
        writer: JsonWriter?,
        value: Boolean?
    ) {
        writer?.value(value)
    }

    override fun toString(): String {
        return "JsonAdapter(Boolean)"
    }
}

internal class NullDoubleAdapter : JsonAdapter<Double>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Double {
        if (reader.next(JsonReader.Token.NUMBER)) {
            return DOUBLE_DEFAULT
        }
        return reader.nextDouble()
    }

    @Throws(IOException::class)
    override fun toJson(
        writer: JsonWriter,
        value: Double?
    ) {
        writer.value(value)
    }

    override fun toString(): String {
        return "JsonAdapter(Double)"
    }
}

internal class NullFloatAdapter : JsonAdapter<Float>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Float? {
        if (reader.next(JsonReader.Token.NUMBER)) {
            return FLOAT_DEFAULT
        }
        val value = reader.nextDouble().toFloat()
        // Double check for infinity after float conversion; many doubles > Float.MAX
        if (!reader.isLenient && java.lang.Float.isInfinite(value)) {
            throw JsonDataException(
                "JSON forbids NaN and infinities: " + value
                        + " at path " + reader.path
            )
        }
        return value
    }

    @Throws(IOException::class)
    override fun toJson(
        writer: JsonWriter,
        value: Float?
    ) {
        // Manual null pointer check for the float.class adapter.
        if (value == null) {
            throw NullPointerException()
        }
        // Use the Number overload so we write out float precision instead of double precision.
        writer.value(value)
    }

    override fun toString(): String {
        return "JsonAdapter(Float)"
    }
}

internal class NullIntAdapter : JsonAdapter<Int>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Int {
        if (reader.next(JsonReader.Token.NUMBER)) {
            return INTEGER_DEFAULT
        }
        return reader.nextInt()
    }

    @Throws(IOException::class)
    override fun toJson(
        writer: JsonWriter,
        value: Int?
    ) {
        writer.value(value)
    }

    override fun toString(): String {
        return "JsonAdapter(Integer)"
    }
}

internal class NullLongAdapter : JsonAdapter<Long>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Long {
        if (reader.next(JsonReader.Token.NUMBER)) {
            return LONG_DEFAULT
        }
        return reader.nextLong()
    }

    @Throws(IOException::class)
    override fun toJson(
        writer: JsonWriter,
        value: Long?
    ) {
        writer.value(value)
    }

    override fun toString(): String {
        return "JsonAdapter(Long)"
    }
}

internal class NullStringAdapter : JsonAdapter<String>() {
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): String {
        if (reader.next(JsonReader.Token.STRING)) {
            return STRING_DEFAULT
        }
        return reader.nextString()
    }

    @Throws(IOException::class)
    override fun toJson(
        writer: JsonWriter,
        value: String?
    ) {
        writer.value(value)
    }

    override fun toString(): String {
        return "JsonAdapter(String)"
    }
}