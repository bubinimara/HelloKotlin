package com.example.hellokotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.util.ImageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 *
 * Created by Davide Parise on 07/09/21.
 */
class MovieAdapter(val imageUtil: ImageUtil):RecyclerView.Adapter<MovieAdapter.Holder>() {
    public class Holder(itemView: View,val util: ImageUtil) : RecyclerView.ViewHolder(itemView) {
        val title:TextView
        val image:ImageView

        init {
            title = itemView.findViewById(R.id.movie_title)
            image = itemView.findViewById(R.id.movie_image)
        }

        fun set(movie:Movie){
            title.text = movie.title

            CoroutineScope(Dispatchers.IO).launch {
                val imageUrlForMovie = util.getImageUrlForMovie(movie)
                withContext(Dispatchers.Main) {
                    Glide.with(itemView)
                        .load(imageUrlForMovie)
                        .centerCrop()
                        .into(image)
                }
            }

        }
    }

     val items:MutableList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_movie,parent,false)
        return Holder(view,imageUtil)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.set(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun add(movies:Collection<Movie>){
        val  size = items.size
        items.addAll(movies)
        notifyItemRangeInserted(size,movies.size)
    }
}