package com.dev.zhaoys.widget.webview

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebView
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.webkit.WebChromeClient



/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/26 0026
 */
class FileChooserWebChromeClient private constructor(internal var build: FileChooserWebChromeClientBuild) :
    WebChromeClient() {

    val uploadMessage: UploadMessage
        get() = build.uploadMessage

    internal class FileChooserWebChromeClientBuild(var callBack: ActivityCallBack) {
        var uploadMessage: UploadMessage = UploadMessage()

        fun build(): FileChooserWebChromeClient {
            return FileChooserWebChromeClient(this)
        }
    }

    interface ActivityCallBack {
        fun fileChooserBack(intent: Intent)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView,
        filePathCallback: ValueCallback<Array<Uri>>,
        fileChooserParams: FileChooserParams
    ): Boolean {
        build.uploadMessage.setUploadMessageAboveL(filePathCallback)
        build.callBack.fileChooserBack(build.uploadMessage.openImageChooserActivity(fileChooserParams.acceptTypes))
        return true
    }

    companion object {
        fun createBuild(callBack: ActivityCallBack): FileChooserWebChromeClient {
            return FileChooserWebChromeClientBuild(callBack).build()
        }
    }
}