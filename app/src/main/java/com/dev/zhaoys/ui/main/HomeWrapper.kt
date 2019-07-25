package com.dev.zhaoys.ui.main

import android.view.View
import com.dev.zhaoys.base.IViewHolder

class HomeWrapper {
    abstract class BaseMainViewHolder(itemView: View, s: HomeSupport) :
        IViewHolder<MainVisitable, HomeSupport>(itemView, s)
}