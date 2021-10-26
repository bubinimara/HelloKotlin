package com.example.hellokotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.util.AppUtils.ImageUtils
import com.example.hellokotlin.ui.util.RateUtil
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 * Created by Davide Parise on 07/09/21.
 */
class MovieAdapter(val listener: AdapterClickListener<Movie>? = null):ListAdapter<Movie,MovieAdapter.Holder>(MovieDiff()) {
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView
        val rate:TextView
        val image:ImageView

        init {
            title = itemView.findViewById(R.id.movie_title)
            rate = itemView.findViewById(R.id.movie_rate)
            image = itemView.findViewById(R.id.movie_image)
        }

        fun set(movie:Movie,listener: AdapterClickListener<Movie>?){
            itemView.setOnClickListener {
                listener?.onItemClicked(movie)
            }

            title.text = movie.title
            rate.setText(R.string.not_rated)// default
            movie.accountState?.rate.let {rated->
                RateUtil.toText(rated)?.let {
                    rate.text = rate.context.getString(R.string.your_rate,it)
                }
            }


            val imageUrlForMovie = ImageUtils.getImageUrlForMovie(movie)
            Glide.with(itemView)
                .load(imageUrlForMovie)
                .centerCrop()
                .into(image)
        }
    }

    class MovieDiff:DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_movie,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.set(getItem(position),listener)
    }

    fun set(movies: Collection<Movie>?){
        submitList(movies?.toMutableList())
    }
}