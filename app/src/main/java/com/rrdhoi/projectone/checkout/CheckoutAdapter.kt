package com.rrdhoi.projectone.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>,
                      private val listener: (Checkout) -> Unit)
    : RecyclerView.Adapter<CheckoutAdapter.LeagueViewHolder>() {

    private lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvKursi: TextView = view.findViewById(R.id.tv_kursi)
        private val tvHarga: TextView = view.findViewById(R.id.tv_harga)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context : Context) {

            // set ke format rupiah
            val localID = Locale("ID", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localID)
            tvKursi.text = formatRupiah.format(data.harga!!.toDouble())

            // jika data kursi dimulai dengan kata Total
            if (data.kursi!!.startsWith("Total")){
                tvKursi.setText(data.kursi)
                tvKursi.setCompoundDrawables(null, null, null, null)
            } else {
                tvKursi.setText("Seat No. ${data.kursi}")
            }

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}