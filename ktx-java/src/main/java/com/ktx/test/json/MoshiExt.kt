package com.ktx.test.json

import com.squareup.moshi.Moshi

/**
 * 仅支持以下类型
 *  Boolean,Double,Float,Integer,Long,String
 *  List
 */
fun moShiCreate(): Moshi = Moshi.Builder()
    .add(NullStandardAdapter.FACTORY)
    .add(NullCollectionAdapter.FACTORY)
    .build()

fun moShiDefault(): Moshi = Moshi.Builder().build()
