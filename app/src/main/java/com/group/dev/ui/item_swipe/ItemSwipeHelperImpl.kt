package com.group.dev.ui.item_swipe

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper

/**
 * 描述:RecyclerView 侧滑ItemTouchHelper实现类
 *
 * author zys
 * create by 2021/3/15
 */
internal class ItemSwipeHelperImpl(lifecycle: Lifecycle) : ItemTouchHelper(SwipeCallbackImpl(lifecycle))


