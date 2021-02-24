package com.rrdhoi.projectone.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.model.Checkout
import com.rrdhoi.projectone.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA1: Boolean = false
    var statusA2: Boolean = false
    var statusA3: Boolean = false
    var statusA4: Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>("data")
        tv_judul.text = data?.judul

        a1.setOnClickListener {
            if( statusA1 == true ) {
                a1.setImageResource(R.drawable.ic_rectangle_empty)
                statusA1 = false
                total -= 1
                beliTiket(total)
            } else {
                a1.setImageResource(R.drawable.ic_rectangle_selected)
                statusA1 = true
                total += 1
                beliTiket(total)

                val dataSelected = Checkout("A1", "35000")
                dataList.add( dataSelected )                                           // ----> kita set nilai di kursi a1 ini
            }
        }

        a2.setOnClickListener {
            if( statusA2 == true ) {
                a2.setImageResource(R.drawable.ic_rectangle_empty)
                statusA2 = false
                total -= 1
                beliTiket(total)
            } else {
                a2.setImageResource(R.drawable.ic_rectangle_selected)
                statusA2 = true
                total += 1
                beliTiket(total)

                val dataSelected = Checkout("A2", "35000")
                dataList.add( dataSelected )                                      // ----> kita set nilai di kursi a1 ini
            }
        }

        btn_beli_tiket.setOnClickListener { 
            startActivity(Intent(this, CheckoutActivity::class.java).putParcelableArrayListExtra("data", dataList))
        }
    }

    private fun beliTiket(total: Int) {
        if( total == 0) {
            btn_beli_tiket.setText("Beli Tiket")
            btn_beli_tiket.visibility = View.INVISIBLE
        } else {
            btn_beli_tiket.setText("Beli Tiket ($total)")
            btn_beli_tiket.visibility = View.VISIBLE
        }
    }
}