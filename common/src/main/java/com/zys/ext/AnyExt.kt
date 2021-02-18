package com.zys.ext

/**
 * 描述:
 *
 * author zys
 * create by 2020/7/27
 */
inline fun <reified R : Any> Any?.asClass(crossinline block: R.() -> Unit = {}): R? {
    return (if (this is R) this else null)?.apply { block(this) }
}