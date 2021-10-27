package com.example.hellokotlin.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.network.ImageLoader
import com.example.hellokotlin.databinding.FragmentMovieBinding
import com.example.hellokotlin.ui.dialog.RateDialogFragment
import com.example.hellokotlin.ui.util.RateUtil
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_ID = "arg_id"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var movieId: Int? = null

    private lateinit var viewModel: MovieViewModel
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
            movieId = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        _viewBinding = FragmentMovieBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            renderData(it)

        })
        viewBinding.fab.setOnClickListener {
            movieId?.let { it1 -> RateDialogFragment.getInstance(it1).show(parentFragmentManager, "RateDialog") }
        }
        // load
        movieId?.let { viewModel.load(it) }
    }

    private fun renderData(movie: Movie) {
        viewBinding.title.text = movie.title
        movie.accountState?.let { accountState ->
            val toText = RateUtil.toText(accountState.rate)
            if(toText != null){
                 viewBinding.rate.text= getString(R.string.your_rate,toText)
            }else{
                 viewBinding.rate.text =   getString(R.string.not_rated)
            }
        }
        viewBinding.description.text = movie.overview
        ImageLoader.load(movie,viewBinding.image)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}