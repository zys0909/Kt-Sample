package com.dev.zhaoys.widget.webview

import android.net.Uri
import android.content.ClipData
import android.content.Intent
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.annotation.TargetApi
import android.app.Activity.RESULT_OK
import android.os.Build
import android.webkit.ValueCallback


/**
 * 描述:
 *
 * author zhaoys
 * create by 2019/7/26 0026
 */
class UploadMessage {

    private var uploadMessage: ValueCallback<Uri>? = null
    private var uploadMessageAboveL: ValueCallback<Array<Uri>>? = null

    fun setUploadMessage(uploadMessage: ValueCallback<Uri>) {
        this.uploadMessage = uploadMessage
    }

    fun setUploadMessageAboveL(uploadMessageAboveL: ValueCallback<Array<Uri>>) {
        this.uploadMessageAboveL = uploadMessageAboveL
    }

    fun openImageChooserActivity(acceptType: Array<String>): Intent {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "*/*"
        i.putExtra(Intent.EXTRA_MIME_TYPES, acceptType)
        return i
    }

    fun openImageChooserActivity(acceptType: String): Intent {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = acceptType
        return Intent.createChooser(i, "Image Chooser")
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (null == uploadMessage && null == uploadMessageAboveL) return
        val result = if (data == null || resultCode != RESULT_OK) null else data.data
        if (uploadMessageAboveL != null) {
            onActivityResultAboveL(requestCode, resultCode, data)
        } else if (uploadMessage != null) {
            uploadMessage!!.onReceiveValue(result)
            uploadMessage = null
        }
    }

    @TargetApi(LOLLIPOP)
    private fun onActivityResultAboveL(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return
        var results: Array<Uri>? = null
        if (resultCode == RESULT_OK) {
            if (intent != null) {
                val dataString = intent.dataString
                val clipData = intent.clipData
                if (clipData != null) {
                    results = Array(clipData.itemCount) { index ->
                        val item = clipData.getItemAt(index)
                        item.uri
                    }
                }
                if (dataString != null)
                    results = arrayOf(Uri.parse(dataString))
            }
        }
        uploadMessageAboveL!!.onReceiveValue(results)
        uploadMessageAboveL = null
    }

    companion object {
        const val FILE_CHOOSER_RESULT_CODE = 10000
    }
}