package com.rrdhoi.projectone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rrdhoi.projectone.onboarding.OnBoardingOneActivity

// ini adalah activity pertama yang akan di run
// tidak ada fitur spesial disini hanya melalukan pending saja
// dan ini adalah push yang pertama

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, OnBoardingOneActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)

    }
}