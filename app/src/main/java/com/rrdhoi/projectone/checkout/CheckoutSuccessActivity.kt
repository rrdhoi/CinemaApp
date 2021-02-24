package com.rrdhoi.projectone.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.home.HomeActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_tiket.setOnClickListener {

        }

        btn_home.setOnClickListener {
            finishAffinity()

            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}