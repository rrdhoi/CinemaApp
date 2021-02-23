package com.rrdhoi.projectone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.rrdhoi.projectone.checkout.PilihBangkuActivity
import com.rrdhoi.projectone.model.Film
import com.rrdhoi.projectone.model.Plays
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Plays>()            // ------> model Plays disini berfungsi buat aktor"nya sendiri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
                .child(data?.judul.toString())
                .child("play")

        // set data yang sudah di kirimkan tadi menggunakan putExtra
        tv_judul.text = data?.judul
        tv_genre.text = data?.genre
        tv_desc.text = data?.desc
        tv_rate.text = data?.rating

        Glide.with(this)
                .load(data?.poster)
                .into(iv_image_poster)

        rv_who_play.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        btn_pilih_bangku.setOnClickListener {
            startActivity(Intent(this@DetailActivity, PilihBangkuActivity::class.java).putExtra("data", dataList))
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                FancyToast.makeText(this@DetailActivity, ""+p0, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                dataList.clear()

                for (getData in p0.children) {
                    val dataActor = getData.getValue(Plays::class.java)
                    dataList.add(dataActor!!)
                }

                rv_who_play.adapter = ActorAdapter(dataList) {

                }
            }

        })
    }
}