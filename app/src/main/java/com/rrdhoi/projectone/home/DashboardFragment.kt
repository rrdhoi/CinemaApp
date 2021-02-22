package com.rrdhoi.projectone.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.model.Film
import com.rrdhoi.projectone.utils.Preferences
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class DashboardFragment : Fragment() {
    private lateinit var preferences: Preferences
    private lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.setText(preferences.getValue("nama"))
        if (preferences.getValue("saldo") == "") {
            currency(preferences.getValue("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
                .load(preferences.getValue("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getData()
    }

    private fun getData(){
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
                FancyToast.makeText(context , "" + databaseError, FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()   // -> di bersihkan dlu datanya biar g ada yang duplikat

                for ( getData in dataSnapshot.children) {
                    val dataFilm = getData.getValue(Film::class.java)
                    dataList.add(dataFilm!!)
                }

//                rv_now_playing.adapter = NowPlayingAdapter(datalist) {
//
//                }
//                rv_coming_soon.adapter = ComingSoonAdapater(datalist) {
//
//                }
            }

        })
    }

    private fun currency(harga : Double, textView: TextView) {
        val localID = Locale("in", "ID")
        val formatCurrency = NumberFormat.getCurrencyInstance(localID)

        textView.setText(formatCurrency.format(harga))
    }

}