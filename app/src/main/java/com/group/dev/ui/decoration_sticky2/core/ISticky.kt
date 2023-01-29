package com.group.dev.ui.decoration_sticky2.core

import android.view.ViewGroup

/**
 * 描述:
 *
 * author zys
 * create by 2021/2/16
 */
interface ISticky {

    fun isGroupHeader(position: Int): Boolean

    fun getGroupId(position: Int): String

    fun  onCreateHeaderViewHolder(parent :ViewGroup) : AbstractHeaderHolder

    fun onBindHeaderViewHolder(holder: AbstractHeaderHolder, position: Int, isFirstItem:Boolean)
}