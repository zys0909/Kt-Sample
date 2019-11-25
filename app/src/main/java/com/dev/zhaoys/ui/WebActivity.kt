package com.dev.zhaoys.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import com.dev.zhaoys.R
import com.dev.zhaoys.app.ExtraConst
import com.dev.zhaoys.base.BaseActivity
import com.dev.zhaoys.widget.webview.BridgeWebViewClient
import kotlinx.android.synthetic.main.activity_web.*

/**
 * WebActivity
 *
 */
private const val ALBUM_REQ = 400

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
            webViewClient = object : BridgeWebViewClient(webView) {
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
                        openAlbumPermissions()
                        return true
                    }
                    return super.onShowFileChooser(p0, p1, p2)
                }
            }


            registerHandler("getBackToList") { _, _ ->
                setResult(Activity.RESULT_OK)
                finish()
            }

            registerHandler("downloadImg") { data, _ ->
                if (!data.isNullOrEmpty()) {
                    storagePermission(data)
                }
            }
        }
        val url = intent.getStringExtra(ExtraConst.WEB_URL)
        webView.loadUrl(url)
    }

    private fun storagePermission(path: String) {
//        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            .subscribe(object : OnNextObserver<Boolean>(compositeDisposable()) {
//                override fun onNext(t: Boolean) {
//                    if (t) {
//                        downloadFile(path)
//                    }
//                }
//            })
    }

    private fun downloadFile(path: String) {
//        ImageDownLoadWork.start(this, path)
    }

    /**
     * 打开本地相册权限
     */
    private fun openAlbumPermissions() {
//        val disposable = RxPermissions(this)
//            .request(Manifest.permission.CAMERA)
//            .subscribeWith(object : ResourceObserver<Boolean>() {
//                override fun onComplete() {
//
//                }
//
//                override fun onNext(t: Boolean) {
//                    if (t) openAlbum()
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//            })
//        compositeDisposable().add(disposable)

    }

    /**
     * 打开相册
     */
    private fun openAlbum() {
//        Matisse.from(this)
//            .choose(MimeType.ofImage())
//            .countable(true)
//            .capture(true)
//            .captureStrategy(
//                CaptureStrategy(
//                    true,
//                    "${BuildConfig.APPLICATION_ID}.fileProvider"
//                )
//            )
//            .maxSelectable(1)
//            .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
//            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//            .thumbnailScale(0.85f)
//            .imageEngine(MatisseGlideEngine())
//            .theme(R.style.Matisse_Dracula)
//            .forResult(ALBUM_REQ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
//            ALBUM_REQ -> {
//                var path: String? = null
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    val pathResult = Matisse.obtainPathResult(data)
//                    path = pathResult?.getOrNull(0)
//                }
//                if (path.isNullOrEmpty()) {
//                    fileCallback?.onReceiveValue(null)
//                } else {
//                    val uri = Uri.parse(path)
//                    fileCallback?.onReceiveValue(arrayOf(uri))
//                }
//                fileCallback = null
//            }
            else -> {
                //empty
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_web, menu)
//        val menuItem = menu?.findItem(R.id.action_web)
//        menuItem?.isVisible = menuState
//        if (webMenuParam != null && !webMenuParam?.text.isNullOrEmpty()) {
//            menuItem?.title = webMenuParam?.text
//        }
//        if (shareContent != null) {
//            val shareItem = menu?.findItem(R.id.action_share)
//            shareItem?.isVisible = true
//        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
//            R.id.action_web -> {
//                webView.loadUrl(webMenuParam?.url)
//                true
//            }
//            R.id.action_share -> {
//                ShareFragment.shareDialog(this)
//                true
//            }
            else -> super.onOptionsItemSelected(item)
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