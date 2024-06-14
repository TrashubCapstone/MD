package com.android.trashub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({

            val intent = Intent(this@SplashActivity, InputActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}