package com.dev.zhaoys.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.dev.zhaoys.R
import com.dev.zhaoys.app.ExtraConst
import com.dev.zhaoys.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

/**
 * WebActivity
 *
 */
@Suppress("DEPRECATION")
class WebActivity : BaseActivity() {
    private var fileCallback: ValueCallback<Array<Uri>>? = null
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
//                    updateTitle(if (p1.isNullOrEmpty()) "" else p1)
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

                override fun onShowFileChooser(
                    p0: WebView?,
                    p1: ValueCallback<Array<Uri>>?,
                    p2: FileChooserParams?
                ): Boolean {
                    if (p1 != null) {
                        fileCallback = p1
//                        openAlbumPermissions()
                        return true
                    }
                    return super.onShowFileChooser(p0, p1, p2)
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

}