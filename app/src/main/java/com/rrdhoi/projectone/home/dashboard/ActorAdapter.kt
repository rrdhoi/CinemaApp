package com.rrdhoi.projectone.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rrdhoi.projectone.R
import com.rrdhoi.projectone.model.Plays

class ActorAdapter(
        private var data: List<Plays>,
        private var listener: (Plays) -> Unit
        ) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    private lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_play, parent, false)

        return ActorViewHolder(
            inflatedView
        )
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size

    class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tv_kursi)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Plays, listener: (Plays) -> Unit, context: Context) {

            tvTitle.text = data.nama

            Glide.with(context)
                    .load(data.url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
