package com.group.dev.main

import android.os.Bundle
import android.view.View
import com.dev.zhaoys.R
import com.dev.zhaoys.app.BaseActivity
import com.zys.ext.fitSystemBar


/**
 * 描述:首页
 *
 * author zys
 */
class MainActivity : BaseActivity() {

    private val flContent: View by lazy { findViewById<View>(R.id.fl_content) }

    override fun layoutId(): Int = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) {
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.fl_content, HomeFragment())
        fm.commit()
    }
}
