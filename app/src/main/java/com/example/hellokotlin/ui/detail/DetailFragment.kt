package com.example.hellokotlin.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.EventObserver
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.databinding.FragmentDetailBinding
import com.example.hellokotlin.ui.adapter.DetailStateAdapter
import com.example.hellokotlin.ui.util.makeMeInvisible
import com.example.hellokotlin.ui.util.makeMeVisible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

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
            arguments = null // next time don't get the current
        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            viewBinding.pager.adapter = DetailStateAdapter(this, it)
        })
        viewModel.eventSelectedMoviePosition.observe(viewLifecycleOwner,EventObserver {
            viewBinding.pager.setCurrentItem( it,false)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showProgress(it)
        })
        viewModel.eventError.observe(viewLifecycleOwner, EventObserver { resString->
            Snackbar.make(viewBinding.root,resString,Snackbar.LENGTH_LONG).show()
        })

        viewModel.load(movieId)
    }

    private fun showProgress(shouldShow: Boolean) {
        if(shouldShow){
            viewBinding.progressBar.makeMeVisible()
            viewBinding.pager.makeMeInvisible()
        }else{
            viewBinding.progressBar.makeMeInvisible()
            viewBinding.pager.makeMeVisible()
        }
    }

}