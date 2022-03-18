package com.demo.newsapplication.utils

import android.app.Activity
import android.app.Dialog
import com.demo.webrtc.R

/**
 * Created by pragya on 25/05/21
 */

class AppLoader(activity: Activity) {
    private var loader: Dialog = Dialog(activity)

    init {
        loader.setContentView(R.layout.dialog_app_loader)
        loader.setCancelable(true)
        loader.window?.setBackgroundDrawable(null)
//        loader.window?.setDimAmount(0f)
        loader.create()
    }

    fun show() {
        loader.show()
    }

    fun dismiss() {
        loader.dismiss()
    }
}