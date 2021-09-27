package com.example.hellokotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.hellokotlin.R
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.databinding.FragmentDetailBinding
import com.example.hellokotlin.ui.adapter.DetailStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var viewBinding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        val movieId = args?.movieId

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            renderView(it)
        })
        viewModel.getMovies()
    }

    private fun renderView(it: Resource<List<Movie>>) {
        when (it) {
            is Resource.Error -> TODO()
            is Resource.Loading -> TODO()
            is Resource.Success -> {
                it.data?.let { movies ->
                    viewBinding.pager.adapter = DetailStateAdapter(this, movies)
                }
            }
        }
    }
}