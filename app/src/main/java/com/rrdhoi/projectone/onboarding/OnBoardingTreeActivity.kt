package com.rrdhoi.projectone.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.sign.signin.SignInActivity

class OnBoardingTreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_tree)

        val btnHome = findViewById<Button>(R.id.btn_home)
        btnHome.setOnClickListener {
            startActivity(Intent(this@OnBoardingTreeActivity, SignInActivity::class.java))

            finishAffinity()
        }
    }
}