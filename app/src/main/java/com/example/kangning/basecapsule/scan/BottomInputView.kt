package com.example.kangning.basecapsule.scan

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import com.example.kangning.basecapsule.R
import kotlinx.android.synthetic.main.bottom_input_view.view.*

/**
 * Created by kangning on 2018/5/28.
 */
class BottomInputView : FrameLayout {

    constructor(context: Context) : super(context) {
        this.addView(customView)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        this.addView(customView)
    }

    private val customView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.bottom_input_view, null, false)
    }

    fun showInput() {
        visibility = View.VISIBLE
        showSoftInputFromWindow(true)
    }

    fun hideInput() {
        visibility = View.GONE
        showSoftInputFromWindow(false)
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    private fun showSoftInputFromWindow(focusable: Boolean) {
        input_edit.isFocusable = focusable
        input_edit.isFocusableInTouchMode = focusable
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (focusable === true) {
            input_edit.requestFocus()
            imm.showSoftInput(input_edit, InputMethodManager.SHOW_IMPLICIT)
        } else {
            imm.hideSoftInputFromWindow(input_edit.windowToken, 0)
        }
    }

}