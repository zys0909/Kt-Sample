package com.ktx.test

import com.squareup.moshi.*
import com.squareup.moshi.JsonAdapter.Factory
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

/**
 * 描述:
 *
 *
 * author zys
 * create by 2020/7/3
 */
/*
internal abstract class NullCollectionAdapter<C : Collection<T>?, T> private constructor(
    private val elementAdapter: JsonAdapter<T>
) : JsonAdapter<C>() {
    abstract fun newCollection(): C

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): C {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull<Any>()
            return newCollection()
        }
        val result = mutableListOf<>()
        reader.beginArray()
        while (reader.hasNext()) {
            result.add(elementAdapter.fromJson(reader))
        }
        reader.endArray()
        return result
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: C?) {
        writer.beginArray()
       value?.run {
           for (element in value) {
               elementAdapter.toJson(writer, element)
           }
       }
        writer.endArray()
    }

    override fun toString(): String {
        return "$elementAdapter.collection()"
    }

    companion object {
        val FACTORY =
            Factory { type, annotations, moshi ->
                val rawType = Types.getRawType(type)
                if (!annotations.isEmpty()) return@Factory null
                if (rawType == MutableList::class.java || rawType == MutableCollection::class.java) {
                    return@Factory newArrayListAdapter<Any>(
                        type,
                        moshi
                    )
                } else if (rawType == MutableSet::class.java) {
                    return@Factory newLinkedHashSetAdapter<Any>(
                        type,
                        moshi
                    )
                }
                null
            }

        fun <T> newArrayListAdapter(
            type: Type?,
            moshi: Moshi
        ): JsonAdapter<Collection<T>> {
            val elementType =
                Types.collectionElementType(
                    type,
                    MutableCollection::class.java
                )
            val elementAdapter: JsonAdapter<T> = moshi.adapter(elementType)
            return object :
                NullCollectionAdapter<Collection<T>, T>(elementAdapter) {
                override fun newCollection(): Collection<T> {
                    return mutableListOf()
                }
            }
        }

        fun <T> newLinkedHashSetAdapter(
            type: Type?,
            moshi: Moshi
        ): JsonAdapter<Set<T>> {
            val elementType =
                Types.collectionElementType(
                    type,
                    MutableCollection::class.java
                )
            val elementAdapter: JsonAdapter<T> = moshi.adapter(elementType)
            return object : NullCollectionAdapter<Set<T>, T>(elementAdapter) {
                override fun newCollection(): Set<T> {
                    return linkedSetOf()
                }
            }
        }
    }

}*/
