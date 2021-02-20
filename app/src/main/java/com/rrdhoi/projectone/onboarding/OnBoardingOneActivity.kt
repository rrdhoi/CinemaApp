package com.rrdhoi.projectone.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.sign.signin.SignInActivity
import com.rrdhoi.projectone.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding_one.*


class OnBoardingOneActivity : AppCompatActivity() {
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preferences = Preferences(this)
        // key harus sama dengan yang dikirim pertama kali di btn_daftar di pencet agar di cek lagi ketika aplikasi dibuka kembali
        val checkStatus = preferences.getValue("statusOnboarding")
        if (checkStatus ==  "1") {
            finishAffinity()

            val goHome = Intent(this@OnBoardingOneActivity, SignInActivity::class.java)
            startActivity(goHome)
        }
        btn_home.setOnClickListener{
            val intent = Intent(this@OnBoardingOneActivity, OnBoardingTwoActivity::class.java)
            startActivity(intent)

            // mengakhiri activity sebelumnya agar tidak kembali ke activity sebelumnya
            finishAffinity()
        }

        btn_daftar.setOnClickListener {
            // di berika value 1 artinya jika btn ini dipencet maka berikan value 1, dan di cek di atas jika nilai 1 sudah ada maka lakukan fun() nya
            preferences.setValue("statusOnboarding", "1")
            val intent = Intent(this@OnBoardingOneActivity, SignInActivity::class.java)
            startActivity(intent)

            // mengakhiri activity sebelumnya agar tidak kembali ke activity sebelumnya
            finishAffinity()
        }
    }
}