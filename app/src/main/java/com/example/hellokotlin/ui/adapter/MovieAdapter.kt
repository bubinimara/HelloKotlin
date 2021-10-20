package com.example.hellokotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.util.AppUtils.ImageUtils


/**
 *
 * Created by Davide Parise on 07/09/21.
 */
class MovieAdapter(val listener: AdapterClickListener<Movie>? = null):RecyclerView.Adapter<MovieAdapter.Holder>() {
    public class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
            movie.accountState?.rate.let {
                if(it != null && it>0)
                rate.text = it.toString()
            }


            val imageUrlForMovie = ImageUtils.getImageUrlForMovie(movie)
            Glide.with(itemView)
                .load(imageUrlForMovie)
                .centerCrop()
                .into(image)
        }
    }

     val items:MutableList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_movie,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.set(items[position],listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun set(movies: Collection<Movie>){
        clear()
        add(movies)
    }

    fun add(movies:Collection<Movie>){
        val  size = items.size
        items.addAll(movies)
        notifyItemRangeInserted(size,movies.size)
    }

    fun clear(){
        val  size = items.size
        if(size>0){
            items.clear()
            notifyItemRangeRemoved(0,size)
        }
    }
}