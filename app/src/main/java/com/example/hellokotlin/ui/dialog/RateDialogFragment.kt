package com.example.hellokotlin.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.EventObserver
import com.example.hellokotlin.databinding.RateDialogBinding
import com.example.hellokotlin.ui.util.makeMeInvisible
import com.example.hellokotlin.ui.util.makeMeVisible
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var viewModel: RateDialogViewModel
    private var _viewBinding: RateDialogBinding? = null
    private val viewBinding get() = _viewBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RateDialogViewModel::class.java)
        arguments?.let {
            movieId = it.getInt(KEY_ID,NO_MOVIE_ID)
            viewModel.load(movieId)
        }
    }

    private fun rateMovie() {
        viewModel.rateMovie(viewBinding.ratingBar.rating)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = RateDialogBinding.inflate(LayoutInflater.from(context),null,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.rate.observe(viewLifecycleOwner, Observer {
            viewBinding.ratingBar.rating = it
        })
        viewModel.closeDialog.observe(viewLifecycleOwner, EventObserver<Unit> {
            dialog?.let {
                        it.dismiss()
            }
        })

        viewModel.showProgress.observe(viewLifecycleOwner, Observer {
            showProgress(it);
        })
        viewModel.showError.observe(viewLifecycleOwner,EventObserver{
            showError(it)
        })

        viewBinding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        viewBinding.btnRate.setOnClickListener {
            rateMovie()
        }
    }

    private fun showProgress(isShowing: Boolean) {
        if(isShowing){
            viewBinding.progressBar.makeMeVisible()
            viewBinding.groupContent.makeMeInvisible()
        }else{
            viewBinding.progressBar.makeMeInvisible()
            viewBinding.groupContent.makeMeVisible()
        }
    }

    private fun showError(@StringRes resourceId:Int) {
            viewBinding.textError.setText(resourceId)
    }

    override fun onDestroy() {
        _viewBinding = null
        super.onDestroy()
    }
}