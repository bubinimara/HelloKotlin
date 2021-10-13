package com.example.hellokotlin.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.databinding.RateDialogBinding
import com.example.hellokotlin.ui.main.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 *
 * Created by Davide Parise on 05/10/21.
 */
@AndroidEntryPoint
class RateDialogFragment:DialogFragment() {
    companion object{
        private val KEY_ID: String = "ARGS_ID"
        private val NO_MOVIE_ID = -1
        fun getInstance( movieId:Int):RateDialogFragment{
            val args  = Bundle().apply {
                putInt(KEY_ID, movieId)
            }
            return RateDialogFragment().apply {
                arguments = args
            }
        }
    }

    private var movieId:Int = NO_MOVIE_ID

    private lateinit var viewModel: DetailViewModel
    private lateinit var viewBinding: RateDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(KEY_ID,NO_MOVIE_ID)
        }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    private fun rateMovie() {
        viewModel.rateMovie(movieId,viewBinding.ratingBar.rating)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = RateDialogBinding.inflate(LayoutInflater.from(context),null,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentMovie.observe(viewLifecycleOwner, Observer {
            val rate =
            viewBinding.ratingBar.rating = if(rate in 0..10)(rate/2.0).toFloat() else 0.0F
        })
        viewModel.ratingResult.observe(viewLifecycleOwner, Observer {
            dialog?.let {dialog ->
                when(it){
                    is Resource.Error ->{
                        Log.e("RateDialog", "Rating movie error: " )
                    }
                    is Resource.Success ->{
                        dialog.dismiss()
                    }
                }
            }
        })
        viewBinding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        viewBinding.btnOk.setOnClickListener {
            rateMovie()
        }
    }
}