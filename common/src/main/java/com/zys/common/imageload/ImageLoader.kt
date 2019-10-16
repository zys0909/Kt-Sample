package com.zys.common.imageload

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class ImageLoader {

    private val requestManager: RequestManager
    private val requestOptions by lazy {
        RequestOptions()
    }
    private val circleRequestOptions by lazy {
        RequestOptions().transform(CircleCrop())
    }
    private val topCircleRequestOptions by lazy {
        RequestOptions().transform(TopCircleCrop())
    }

    constructor(activity: FragmentActivity) {
        requestManager = Glide.with(activity)
    }

    constructor(fragment: Fragment) {
        requestManager = Glide.with(fragment)
    }

    fun load(
        view: ImageView,
        url: String?,
        placeholder: Int = 0,
        error: Int = 0,
        crossFade: Boolean = true
    ) {
        requestManager
            .load(url)
            .apply(
                requestOptions.autoClone()
                    .placeholder(placeholder)
                    .error(error)
            )
            .apply {
                if (crossFade) {
                    transition(DrawableTransitionOptions.withCrossFade())
                }
            }
            .into(view)
    }

    fun loadCircle(
        view: ImageView,
        url: String?,
        placeholder: Int = 0,
        error: Int = 0
    ) {
        requestManager
            .load(url)
            .apply(
                circleRequestOptions.autoClone()
                    .placeholder(placeholder)
                    .error(error)
            )
            .into(view)
    }

    fun loadTopCircle(
        view: ImageView,
        url: String?,
        placeholder: Int = 0,
        error: Int = 0
    ) {
        requestManager
            .load(url)
            .apply(
                topCircleRequestOptions.autoClone()
                    .placeholder(placeholder)
                    .error(error)
            )
            .into(view)
    }
}