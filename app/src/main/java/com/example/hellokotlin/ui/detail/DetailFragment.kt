package com.example.hellokotlin.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.databinding.FragmentDetailBinding
import com.example.hellokotlin.ui.adapter.DetailStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var viewBinding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var movieId:Int = 0

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
        args?.movieId?.let {
            movieId = it
        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            renderView(it)
        })
        viewModel.load(savedInstanceState != null)
    }

    private fun renderView(it: Resource<List<Movie>>) {
        when (it) {
            is Resource.Error -> TODO()
            is Resource.Loading -> TODO()
            is Resource.Success -> {
                it.data?.let { movies ->
                    viewBinding.pager.adapter = DetailStateAdapter(this, movies)
                    viewBinding.pager.setCurrentItem(  findPositionById(movies,movieId),false)
                }
            }
        }
    }

    private fun findPositionById(movies:List<Movie>,movieId: Int): Int {
        for (i in 0..movies.size ){
            if(movies[i].id == movieId)
                return i
        }
        return 0
    }

}