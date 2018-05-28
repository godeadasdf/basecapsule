package com.example.kangning.basecapsule.scan

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.kangning.basecapsule.R
import com.ofo.scan.listeners.IScanCallback
import com.ofo.scan.views.BaseScanView
import com.ofo.scan.views.ScanViewAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.camera_scanner_view.view.*
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT


/**
 * Created by kangning on 2018/5/28.
 *
 * 相机扫码常用封装
 *
 * 考虑情况
 * 3.LifecycleObserver
 * 4.RxStream封装数据结果
 * 5.LifeCycleObserver监听LifeCycleOwner（activity or fragment）事件
 * 对应activity配置如下属性
 * android:windowSoftInputMode="adjustPan|stateHidden"
 */

//todo 找到黄框的具体位置 马勒基。。。
class CameraScanner : FrameLayout, BaseScanner, IScanCallback, LifecycleObserver {

    var attachedActivity: Activity? = null
    private lateinit var lifecycle: Lifecycle
    lateinit var bottomInputView: BottomInputView


    private var baseView: View
    private lateinit var scannerView: BaseScanView

    private var isFlashOn = false
    private var isInputting = false

    constructor(context: Context) : super(context) {
        baseView = initView(context)
        setupScannerViewAdapter()
        addView(baseView)
        initListener()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        baseView = initView(context)
        setupScannerViewAdapter()
        addView(baseView)
        initListener()
    }

    private val initView: (Context) -> View = {
        LayoutInflater.from(it).inflate(R.layout.camera_scanner_view, null, false)
    }

    private fun setupScannerViewAdapter() {
        val scanViewAdapter = baseView.findViewById(R.id.scanner_view) as ScanViewAdapter
        scannerView = scanViewAdapter.getScannerView(1)
        scannerView.setIsNeedOpenFlashlightInDarkEnv(false)
        scannerView.setScanAnimationDuration(2000)
        scannerView.scanTimeoutDuration = 30000
        scannerView.setScanCallback(this)
    }

    private val initListener: () -> Unit = {
        flashlight.setOnClickListener {
            when (isFlashOn) {
                true -> {
                    scannerView.turnOffFlashlight()
                    isFlashOn = false
                    flash_state.text = "打开手电筒"
                    flashlight.setImageResource(R.drawable.flashllight_on)
                }
                false -> {
                    scannerView.turnOnFlashlight()
                    isFlashOn = true
                    flash_state.text = "关闭手电筒"
                    flashlight.setImageResource(R.drawable.flashlight_off)
                }
            }
        }
        input_text.setOnClickListener {
            if (attachedActivity != null) {
                when (isInputting) {
                    true -> {
                        isInputting = false
                        input_text.setImageResource(R.drawable.keyboard_show)
                        bottomInputView.showInput()
                    }
                    false -> {
                        isInputting = true
                        input_text.setImageResource(R.drawable.keyboard_hide)
                        bottomInputView.hideInput()
                    }
                }
            }
        }

    }



    val attachLifecycle: (Lifecycle) -> Unit = {
        lifecycle = it
        it.addObserver(this)
    }

    private val detachLifecycle: () -> Unit = {
        lifecycle.removeObserver(this)
    }

    private val scanResultPublisher: PublishSubject<String> by lazy {
        PublishSubject.create<String>()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        scanning = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d("aaa", "onResume")
        if (scanning === true) {
            startScan()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d("aaa", "onPause")
        if (scanning === true) {
            stopScan()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        scannerView.onDestroy()
        detachLifecycle()
        attachedActivity = null
    }

    var scanning: Boolean = false

    fun startScan(): Observable<String> {
        scannerView.reScan()
        scanning = true
        return scanResultPublisher
    }


    fun stopScan() {
        scannerView.stopScan()
        scanning = false
    }


    override fun onScanSucceed(content: String): Boolean {

        if (content !== "" || scanning === true) {
            scanResultPublisher.onNext(content)
        }

        return false
    }

    override fun onTorchOpened() {
    }

    override fun onTorchClosed() {
    }

    override fun onScanTimeout() {
        scanResultPublisher.onError(Exception("onScanTimeout"))
    }

    override fun onScanError() {
        scanResultPublisher.onError(Exception("onScanError"))
    }
}