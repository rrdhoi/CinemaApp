package com.rrdhoi.projectone.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.rrdhoi.projectone.R

class OnBoardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        val btnHome = findViewById<Button>(R.id.btn_home)
        btnHome.setOnClickListener {
            val intent = Intent(this@OnBoardingTwoActivity, OnBoardingTreeActivity::class.java)
            startActivity(intent)

            // agar tidak kembali ke activity sebelumnya
            finishAffinity()
        }
    }
}