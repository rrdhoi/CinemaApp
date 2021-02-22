package com.rrdhoi.projectone.sign.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.rrdhoi.projectone.home.HomeActivity
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.sign.signup.SignUpActivity
import com.rrdhoi.projectone.utils.Preferences
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    private lateinit var iUsername : String
    private lateinit var iPassword : String
    private lateinit var mDatabase: DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        preferences.setValue("onboarding", "1")
        val checkStatus = preferences.getValue("status")
        if ( checkStatus == "1" ) {
            // mengecek activity jika status data sudah ada isinya artinya sudah login maka akan melewatkan acktivity ini dan goHome intent home
            finishAffinity()

            val goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_home.setOnClickListener {
            // mengubah isi inputan menjadi string
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            when {
                iUsername == "" -> {
                    et_username.error = "Silahkan Masukkan Username Anda"
                    et_username.requestFocus()
                }
                iPassword == "" -> {
                    et_password.error = "Silahkan Masukkan Password Anda"
                    et_password.requestFocus()
                }
                else -> {
                    pushLogin(iUsername ,iPassword)
                }
            }
        }

        btn_daftar.setOnClickListener {
            val goSignUp = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(goSignUp)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshoot: DataSnapshot) {
                val user = dataSnapshoot.getValue(User::class.java)

                if (user == null){
                    FancyToast.makeText(this@SignInActivity , "User tidak ditemukan", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
                } else {
                    if (user.password == iPassword) {
                        preferences.setValue("nama", user.nama.toString())
                        preferences.setValue("email", user.email.toString())
                        preferences.setValue("saldo", user.saldo.toString())
                        preferences.setValue("url", user.url.toString())
                        preferences.setValue("username", user.username.toString())
                        preferences.setValue("status", "1")

                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                        FancyToast.makeText(this@SignInActivity , "Berhasil login", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show()
                    } else {
                        FancyToast.makeText(this@SignInActivity , "Password anda salah", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
                    }
                }
            }

            override fun onCancelled(dataSnapshoot: DatabaseError) {
                FancyToast.makeText(this@SignInActivity , "User tidak ditemukan", FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
            }

        })
    }
}