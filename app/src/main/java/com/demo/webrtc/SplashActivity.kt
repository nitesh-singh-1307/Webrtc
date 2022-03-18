package com.demo.webrtc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.newsapplication.base.BaseActivity
import com.demo.webrtc.databinding.ActivitySplashBinding
import com.muzville.ui.auth.login.LoginActivity
import org.jetbrains.anko.startActivity

class SplashActivity :  BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, 3000)
    }
}