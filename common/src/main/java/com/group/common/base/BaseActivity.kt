package com.group.common.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.group.common.R
import com.group.common.core.ExtraConst
import com.group.common.ext.fitSystemBar

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        val title = intent.getStringExtra(ExtraConst.ACTIVITY_TITLE)
        title?.let {
            initToolbar(it)
        }
        init(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun init(savedInstanceState: Bundle?)

    protected fun initToolbar(title: CharSequence? = null, showHomeAsUp: Boolean = true) {
        findViewById<Toolbar>(R.id.toolbar)?.let {
            it.fitSystemBar()
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp)
            supportActionBar?.title = title
            supportActionBar?.setHomeButtonEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}