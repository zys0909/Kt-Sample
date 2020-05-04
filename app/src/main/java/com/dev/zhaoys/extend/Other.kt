package com.dev.zhaoys.extend

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/26 0026
 */
internal var RecyclerView.orientation: Int
    set(value) {
        this.layoutManager = LinearLayoutManager(this.context, value, false)
    }
    get() = (this.layoutManager as LinearLayoutManager).orientation

internal fun <T> AppCompatActivity.openActivity(clazz: Class<T>) {
    this.startActivity(Intent(this, clazz))
    this.overridePendingTransition(0, 0)
}

internal fun Context.toast(msg: String) {
    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
}


