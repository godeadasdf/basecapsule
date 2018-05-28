package com.example.kangning.basecapsule

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        camera_scanner.attachLifecycle(lifecycle)
        camera_scanner.attachedActivity = this
        camera_scanner.bottomInputView = bottom_input


        /*start.setOnClickListener {
            camera_scanner.startScan().subscribe(object : Observer<String> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: String) {
                    Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {

                }
            })
        }
        stop.setOnClickListener {
            camera_scanner.stopScan()
        }
*/
    }
}
