package com.example.hellokotlin.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hellokotlin.EventObserver
import com.example.hellokotlin.R
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.databinding.MainFragmentBinding
import com.example.hellokotlin.ui.adapter.AdapterClickListener
import com.example.hellokotlin.ui.adapter.MovieAdapter
import com.example.hellokotlin.ui.login.LoginActivity
import com.example.hellokotlin.ui.util.makeMeInvisible
import com.example.hellokotlin.ui.util.makeMeVisible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _viewBinding: MainFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!

    private var rvMoviewAdaper = MovieAdapter { item ->
        val action =
            MainFragmentDirections.movieDetailAction(item.id)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _viewBinding = MainFragmentBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.rvMovies.layoutManager = GridLayoutManager(context,3)
        viewBinding.rvMovies.adapter = rvMoviewAdaper

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.movies.observe(viewLifecycleOwner, {
            rvMoviewAdaper.set(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { showProgress(it) })
        viewModel.eventError.observe(viewLifecycleOwner,EventObserver{
            Snackbar.make(viewBinding.root,it,Snackbar.LENGTH_LONG).show()
        })
        viewModel.eventLogout.observe(viewLifecycleOwner,EventObserver{
            goToLogin()
        })

        viewModel.load()
    }

    override fun onDestroy() {
        _viewBinding = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout ->{
                //TODO:show Dialog
                viewModel.logout()
                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun goToLogin() {
        // @Todo:check if resumed
        val intent= Intent(context,LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
    private fun showProgress(isShowing:Boolean){
        if(isShowing){
            viewBinding.progressBar.makeMeVisible()
            viewBinding.rvMovies.makeMeInvisible()
        }else{
            viewBinding.progressBar.makeMeInvisible()
            viewBinding.rvMovies.makeMeVisible()
        }
    }
}

