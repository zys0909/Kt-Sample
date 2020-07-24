package com.ktx.test

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException

/**
 * 描述:基础类型
 *
 *
 * author zys
 * create by 2020/7/3 13:02
 */
internal object NullStandardAdapter {
    private const val BOOLEAN_DEFAULT = false
    private const val DOUBLE_DEFAULT = 0.0
    private const val FLOAT_DEFAULT = 0f
    private const val INTEGER_DEFAULT = 0
    private const val LONG_DEFAULT = 0L
    private const val STRING_DEFAULT = ""
    val FACTORY = JsonAdapter.Factory { type, _, _ ->
            if (type === Boolean::class.java) return@Factory BOOLEAN_JSON_ADAPTER
            if (type === Double::class.java) return@Factory DOUBLE_JSON_ADAPTER
            if (type === Float::class.java) return@Factory FLOAT_JSON_ADAPTER
            if (type === Int::class.java) return@Factory INTEGER_JSON_ADAPTER
            if (type === Long::class.java) return@Factory LONG_JSON_ADAPTER
            if (type === String::class.java) STRING_JSON_ADAPTER else null
        }
    private val BOOLEAN_JSON_ADAPTER: JsonAdapter<Boolean> =
        object : JsonAdapter<Boolean>() {
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Boolean {
                if (reader.peek() == JsonReader.Token.NULL) {
                    reader.nextNull<Any>()
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
    private val DOUBLE_JSON_ADAPTER: JsonAdapter<Double> =
        object : JsonAdapter<Double>() {
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Double {
                if (reader.peek() == JsonReader.Token.NULL) {
                    reader.nextNull<Any>()
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
    private val FLOAT_JSON_ADAPTER: JsonAdapter<Float> =
        object : JsonAdapter<Float>() {
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Float? {
                if (reader.peek() == JsonReader.Token.NULL) {
                    reader.nextNull<Any>()
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
    private val INTEGER_JSON_ADAPTER: JsonAdapter<Int> =
        object : JsonAdapter<Int>() {
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Int {
                if (reader.peek() == JsonReader.Token.NULL) {
                    reader.nextNull<Any>()
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
    private val LONG_JSON_ADAPTER: JsonAdapter<Long> =
        object : JsonAdapter<Long>() {
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): Long {
                if (reader.peek() == JsonReader.Token.NULL) {
                    reader.nextNull<Any>()
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
    private val STRING_JSON_ADAPTER: JsonAdapter<String> =
        object : JsonAdapter<String>() {
            @Throws(IOException::class)
            override fun fromJson(reader: JsonReader): String {
                if (reader.peek() == JsonReader.Token.NULL) {
                    reader.nextNull<Any>()
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
}