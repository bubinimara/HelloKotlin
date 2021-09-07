package com.example.hellokotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.Movie


/**
 *
 * Created by Davide Parise on 07/09/21.
 */
class MovieAdapter:RecyclerView.Adapter<MovieAdapter.Holder>() {
    public class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView

        init {
            title = itemView.findViewById(R.id.movie_title)
        }

        fun set(movie:Movie){
            title.text = movie.title
        }
    }

     val items:MutableList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_movie,parent,false)
        return Holder(view)
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