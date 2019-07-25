package com.dev.zhaoys.imageLoad

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class StringGlideEngine : ImageLoad<String> {

    private var requestManager: RequestManager
    private var requestOptions: RequestOptions

    constructor(activity: AppCompatActivity) {
        requestManager = Glide.with(activity)
        requestOptions = RequestOptions()
    }

    constructor(fragment: Fragment) {
        requestManager = Glide.with(fragment)
        requestOptions = RequestOptions()
    }

    override fun load(
        view: ImageView,
        source: String?,
        placeholder: Int,
        error: Int,
        fallback: Int,
        circleCrop: Boolean,
        crossFade: Boolean
    ) {
        val options = when{
            circleCrop->{
                requestOptions
                    .autoClone()
                    .placeholder(placeholder)
                    .error(error)
                    .fallback(fallback)
                    .transform(CircleCrop())
            }
            else->{
                requestOptions
                    .autoClone()
                    .placeholder(placeholder)
                    .error(error)
                    .fallback(fallback)
            }
        }
        when {
            crossFade -> {
                requestManager
                    .load(source)
                    .apply(options)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
            }
            else -> {
                requestManager
                    .load(source)
                    .apply(options)
                    .into(view)
            }
        }
    }

}