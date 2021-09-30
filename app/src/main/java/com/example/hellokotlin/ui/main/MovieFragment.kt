package com.example.hellokotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.R
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_ID = "arg_id"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var id: Int? = null

    private lateinit var viewModel:DetailViewModel
    private lateinit var viewBinding:FragmentMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewBinding = FragmentMovieBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentMovie.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Error -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success -> {
                    viewBinding.title.text = it.data?.title
                }
            }

        })
        id?.let { viewModel.getMovieById(it) }
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID,id)
                }
            }
    }
}