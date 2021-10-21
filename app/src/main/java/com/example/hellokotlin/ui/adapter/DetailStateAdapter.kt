package com.example.hellokotlin.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.ui.detail.MovieFragment


/**
 *
 * Created by Davide Parise on 27/09/21.
 */
class DetailStateAdapter(fragment: Fragment,var movies:List<Movie>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun createFragment(position: Int): Fragment {
       return MovieFragment.newInstance(movies[position].id)
    }
}