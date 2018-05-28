package com.example.kangning.basecapsule.scan

/**
 * Created by kangning on 2018/5/28.
 */
import java.util.Timer
import java.util.TimerTask
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object InputTools {

    //隐藏虚拟键盘
    fun HideKeyboard(v: View) {
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)

        }
    }

    //显示虚拟键盘
    fun ShowKeyboard(v: View) {
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED)

    }

    //强制显示或者关闭系统键盘
    fun KeyBoard(txtSearchKey: EditText, status: String) {

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val m = txtSearchKey.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (status == "open") {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED)
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.windowToken, 0)
                }
            }
        }, 300)
    }

    //通过定时器强制隐藏虚拟键盘
    fun TimerHideKeyboard(v: View) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
                }
            }
        }, 10)
    }

    //输入法是否显示着
    fun KeyBoard(edittext: EditText): Boolean {
        var bool = false
        val imm = edittext.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            bool = true
        }
        return bool

    }
}
