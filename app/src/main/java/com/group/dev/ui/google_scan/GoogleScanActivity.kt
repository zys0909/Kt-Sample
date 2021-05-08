package com.group.dev.ui.google_scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.os.Handler
import android.util.Size
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.group.dev.R
import timber.log.Timber
import java.util.concurrent.Executors


class GoogleScanActivity : AppCompatActivity() {

    private val previewView by lazy { findViewById<PreviewView>(R.id.previewView) }
    private val overlay by lazy { findViewById<ScanOverlay>(R.id.overlay) }

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private var listener: GoogleScanActivity.OverlayListener? = null

    private var camera: Camera? = null

    private var scaleX = 0f

    private var scaleY = 0f

    companion object {
        const val SCAN_RESULT = "BarcodeScanningActivity.scan_result"
        const val REQUEST_PERMISSION = 12345
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_scan)
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_PERMISSION
        )
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        listener = OverlayListener()
        overlay.viewTreeObserver.addOnGlobalLayoutListener(listener)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    inner class OverlayListener : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                bindScan(cameraProvider, overlay.width, overlay.height)
            }, ContextCompat.getMainExecutor(this@GoogleScanActivity))
            overlay.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindScan(cameraProvider: ProcessCameraProvider, width: Int, height: Int) {

        Timber.tag("测试TAG").i("bindScan: width:$width height:$height")

        val preview: Preview = Preview.Builder()
            .build()

        //绑定预览
        preview.setSurfaceProvider(previewView.surfaceProvider)

        //使用后置相机
        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        //配置图片扫描
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(width, height))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        //绑定图片扫描解析
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(),
            QRCodeAnalyser { barcode, imageWidth, imageHeight ->
                //解绑当前所有相机操作
                cameraProvider.unbindAll()
                //初始化缩放比例
                initScale(imageWidth, imageHeight)
                barcode.boundingBox?.let {//扫描二维码的外边框矩形
                    overlay.addRect(translateRect(it))
                    Timber.tag("测试TAG")
                        .i("bindScan: left:${it.left} right:${it.right} top:${it.top} bottom:${it.bottom}")
                }
                Handler().postDelayed({
                    //延迟1S后返回结果
                    val intent = Intent()
                    intent.putExtra(SCAN_RESULT, barcode.rawValue)
                    setResult(RESULT_OK, intent)
                }, 1000)
            })
        //将相机绑定到当前控件的生命周期
        camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageAnalysis,
            preview
        )
    }

    private fun translateX(x: Float): Float = x * scaleX
    private fun translateY(y: Float): Float = y * scaleY

    //将扫描的矩形换算为当前屏幕大小
    private fun translateRect(rect: Rect) = RectF(
        translateX(rect.left.toFloat()),
        translateY(rect.top.toFloat()),
        translateX(rect.right.toFloat()),
        translateY(rect.bottom.toFloat())
    )

    private fun initScale(imageWidth: Int, imageHeight: Int) {
        if (isPortraitMode(this)) {
            scaleY = overlay.height.toFloat() / imageWidth.toFloat()
            scaleX = overlay.width.toFloat() / imageHeight.toFloat()
        } else {
            scaleY = overlay.height.toFloat() / imageHeight.toFloat()
            scaleX = overlay.width.toFloat() / imageWidth.toFloat()
        }
    }
}