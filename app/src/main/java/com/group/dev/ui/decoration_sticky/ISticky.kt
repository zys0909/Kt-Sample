package com.group.dev.ui.decoration_sticky

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
interface ISticky {

    fun isGroupHeader(position: Int): Boolean

    fun getGroupName(position: Int): String
}