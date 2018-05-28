package com.example.kangning.basecapsule

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LifecycleOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        setContentView(R.layout.activity_main)
        camera_scanner.attachLifecycle(lifecycle)
        camera_scanner.attachedActivity = this

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
