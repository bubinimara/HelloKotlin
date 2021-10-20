package com.example.hellokotlin.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.util.AppUtils
import com.example.hellokotlin.databinding.FragmentMovieBinding
import com.example.hellokotlin.ui.detail.DetailViewModel
import com.example.hellokotlin.ui.dialog.RateDialogFragment
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

    private lateinit var viewModel: DetailViewModel
    private var _viewBinding:FragmentMovieBinding? = null
    private val viewBinding  get() = _viewBinding!!

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID,id)
                }
            }
    }

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
        _viewBinding = FragmentMovieBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentMovie.observe(viewLifecycleOwner, Observer {
            renderData(it)

        })
        viewBinding.fab.setOnClickListener {
        kotlin.runCatching {
            id?.let { it1 -> RateDialogFragment.getInstance(it1).show(parentFragmentManager, "RateDialog") }
        }
        }
        // load
        id?.let { viewModel.getMovieById(it) }
    }

    private fun renderData(it: Resource<Movie>) {
        when (it) {
            is Resource.Error -> TODO()
            is Resource.Loading -> TODO()
            is Resource.Success -> {
                it.data?.let { movie ->
                    viewBinding.title.text = movie.title
                    movie.accountState?.let { accountState ->
                        viewBinding.rate.text = accountState.rate.toString()
                    }
                    viewBinding.description.text = movie.overview
                    Glide.with(viewBinding.image)
                        .load(AppUtils.ImageUtils.getImageUrlForMovie(movie))
                        .into(viewBinding.image)

                }
            }
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}