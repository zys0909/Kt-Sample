package com.zys.common.adapter

import com.zys.common.imageload.ImageLoader

class RecyclerSupport {

    lateinit var imageLoader: ImageLoader

    var onClickCallback: ((position: Int, type: Int) -> Unit)? = null

    var onRefresh: (() -> Unit)? = null

    var onLoadComplete: ((pageIndex: Int, pageCount: Int, total: Int) -> Unit)? = null

    var onRetry: (() -> Unit)? = null

    infix fun clickCallback(block: ((position: Int, type: Int) -> Unit)) {
        onClickCallback = block
    }

    infix fun refresh(block: () -> Unit) {
        onRefresh = block
    }

    infix fun loadComplete(block: (pageIndex: Int, pageCount: Int, total: Int) -> Unit) {
        onLoadComplete = block
    }

    infix fun retry(block: () -> Unit) {
        onRetry = block
    }
}