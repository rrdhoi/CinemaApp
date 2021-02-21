package com.rrdhoi.projectone.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.sign.signin.User
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    private lateinit var sUsername : String
    private lateinit var sPassword : String
    private lateinit var sNama : String
    private lateinit var sEmail : String

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = mFirebaseInstance.getReference("User")

        btn_lanjutkan.setOnClickListener {
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_nama.text.toString()
            sEmail = et_email.text.toString()

            when {
                sUsername == "" -> {
                    et_username.error = "Silahkan masukkan username anda"
                    et_username.requestFocus()
                }
                sPassword == "" -> {
                    et_password.error = "Silahkan masukkan password anda"
                    et_password.requestFocus()
                }
                sNama == "" -> {
                    et_nama.error = "Silahkan masukkan nama anda"
                    et_nama.requestFocus()
                }
                sEmail == "" -> {
                    et_email.error = "Silahkan masukkan email anda"
                    et_email.requestFocus()
                }
                else -> {
                    saveUsername(sUsername, sPassword, sNama, sEmail)
                }
            }

        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama
        user.email = sEmail

        checkingUsername(sUsername, user)
    }

    private fun checkingUsername(sUsername: String, user: User) {
        mDatabase.child(sUsername).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                FancyToast.makeText(this@SignUpActivity , ""+databaseError, FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.getValue(User::class.java)

                if (userData == null) {
                    mDatabase.child(sUsername).setValue(user)

                    val goSignUpPhotoscreen = Intent(this@SignUpActivity, SignUpPhotoScreenActivity::class.java).putExtra("nama", user.nama)
                    startActivity(goSignUpPhotoscreen)

                    finishAffinity()
                    FancyToast.makeText(this@SignUpActivity , "User berhasil dibuat", FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show()
                } else {
                    FancyToast.makeText(this@SignUpActivity , "Username sudah digunakan", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                }
            }
        })
    }
}