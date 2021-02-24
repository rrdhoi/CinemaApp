@file:Suppress("UNCHECKED_CAST")

package com.rrdhoi.projectone.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.home.HomeActivity
import com.rrdhoi.projectone.model.Checkout
import com.rrdhoi.projectone.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total: Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for (a in dataList.indices) {
            total += dataList[a].harga!!.toInt()
        }

        val totalHarga = Checkout("Total harus dibayar", (total*2).toString())
        dataList.add(totalHarga)

        rv_checkout.layoutManager = LinearLayoutManager(this)
        rv_checkout.adapter = CheckoutAdapter(dataList) {

        }

        btn_bayar_sekarang.setOnClickListener {
            startActivity(Intent(this@CheckoutActivity, CheckoutSuccessActivity::class.java))
        }
        btn_home.setOnClickListener {
            startActivity(Intent(this@CheckoutActivity, HomeActivity::class.java))
        }
    }


}