package com.group.dev.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import com.group.common.base.BaseActivity
import com.group.common.core.ExtraConst
import com.group.dev.R
import kotlinx.android.synthetic.main.activity_web.*
import java.util.*

/**
 * WebActivity
 *
 */
@Suppress("DEPRECATION")
class WebActivity : BaseActivity() {
    private var menuState = false
    override fun layoutId() = R.layout.activity_web

    override fun init(savedInstanceState: Bundle?) {
        webView.settings.apply {
            loadWithOverviewMode = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            useWideViewPort = true
            textSize = WebSettings.TextSize.NORMAL
            loadsImagesAutomatically = true
            userAgentString = webView.settings.userAgentString
            domStorageEnabled = true
            allowContentAccess = true
            setAppCacheEnabled(true)
            javaScriptEnabled = true
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
        }

        webView.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(webView: WebView, url: String) = when {
                    url.startsWith("tel:") -> {
                        dial(url.replace("tel:", ""))
                        true
                    }
                    else -> {
                        if (menuState) {
                            menuState = false
                            invalidateOptionsMenu()
                        }
                        super.shouldOverrideUrlLoading(webView, url)
                    }
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(p0: WebView?, p1: String?) {
                    toolbar.title = if (p1.isNullOrEmpty()) "" else p1
                }

                override fun onProgressChanged(p0: WebView?, p1: Int) {
                    when (p1) {
                        in 0..99 -> {
                            progressBar.apply {
                                visibility = View.VISIBLE
                                progress = p1
                            }
                        }
                        else -> progressBar.visibility = View.GONE
                    }
                }
            }
        }
        val url = intent.getStringExtra(ExtraConst.WEB_URL)
        webView.loadUrl(url)
    }


    override fun onBackPressed() = when {
        webView.canGoBack() -> webView.goBack()
        else -> super.onBackPressed()
    }

    override fun onDestroy() {
        if (webView != null && webView.parent != null) {
            val parent = webView.parent as ViewGroup
            parent.removeView(webView)
        }
        webView.apply {
            stopLoading()
            @Suppress("DEPRECATION")
            settings.javaScriptEnabled = false
            clearHistory()
            @Suppress("DEPRECATION")
            clearView()
            removeAllViews()
            destroy()
        }
        super.onDestroy()
    }

    private fun dial(phone: String?) {
        when {
            phone.isNullOrEmpty() -> {
                AlertDialog.Builder(this).setMessage(String.format(Locale.CHINA, "您要拨打的号码为空！"))
                    .setPositiveButton("确定", null).create().show()
            }
            else -> {
                AlertDialog.Builder(this).setMessage(String.format(Locale.CHINA, "确认拨打:$phone"))
                    .setPositiveButton("确定") { _, _ -> dialing(phone) }
                    .setNegativeButton("取消", null).create().show()
            }
        }
    }

    private var phone = ""

    @SuppressLint("MissingPermission")
    private fun dialing(phone: String) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.phone = phone
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 999)
        } else {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")))
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 999 && phone.isNotEmpty())
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")))
    }
}