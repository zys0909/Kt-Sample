package com.dev.zhaoys.app

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.zys.widget.AppToolBar

/**
 * 描述:简单的RecycleView 基类
 *
 * author zys
 * create by 2021/2/21
 */
abstract class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolBar = AppToolBar(this).apply {
            setBackgroundColor(0xFFEEEEEE.toInt())
        }
        val recyclerView = RecyclerView(this)
        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(toolBar, -1, -2)
            addView(recyclerView, -1, -1)
        })
        init(toolBar, recyclerView)
    }

    abstract fun init(toolBar: AppToolBar, recyclerView: RecyclerView)
}