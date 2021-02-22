package com.rrdhoi.projectone.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rrdhoi.projectone.home.HomeActivity
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.utils.Preferences
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*
import java.util.*

class SignUpPhotoScreenActivity : AppCompatActivity(){

    private var statusAdd: Boolean = false
    private lateinit var filePath: Uri

    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        tv_hello.text = "Selamat datang \n"+intent.getStringExtra("nama")

        iv_add.setOnClickListener {
            // status true itu artinya imagenya ada dan kita
            if(statusAdd == true) {
                statusAdd = false
                btn_save.visibility = View.INVISIBLE
                iv_add.setImageResource(R.drawable.ic_baseline_add_24)
                iv_profile.setImageResource(R.drawable.user_pic)
            } else {
                ImagePicker.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .cameraOnly()
                        .start()
            }
        }

        btn_home.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()


                // method UUID berfungsi agar file tidak bentrok
                val ref : StorageReference = storageReference.child("images/"+ UUID.randomUUID().toString())            // <----- ini adalah tempat penyimpanan file gambarnya
                ref.putFile(filePath)
                    .addOnSuccessListener {
                            progressDialog.dismiss()
                            FancyToast.makeText(this, "Uploaded",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show()

                            ref.downloadUrl.addOnSuccessListener {
                                preferences.setValue("url", it.toString())

                                finishAffinity()
                                startActivity(Intent(this, HomeActivity::class.java))

                            }
                        }
                    .addOnFailureListener {
                            progressDialog.dismiss()
                            FancyToast.makeText(this, "Upload Failed",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
                        }
                    .addOnProgressListener {
                            taskSnapshot -> val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                            progressDialog.setMessage("Uploaded "+progress.toInt()+" %")
                        }
            }
        }
    }

    override fun onBackPressed() {
        FancyToast.makeText(this, "Upload gambar anda",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
        {
            // statusAdd ini digunakan untuk mengganti icon
            statusAdd = true
            // Image Uri tidak akan null jika RESULT_OK
            filePath = data?.getData()!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

            btn_save.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_baseline_delete_24)
        }
            else if (resultCode == ImagePicker.RESULT_ERROR)
        {
                FancyToast.makeText(this, ImagePicker.getError(data),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
        }
            else
        {
                FancyToast.makeText(this, "Task Cancelled",FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show()
        }
    }
}