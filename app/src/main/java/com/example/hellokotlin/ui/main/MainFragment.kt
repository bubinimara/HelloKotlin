package com.example.hellokotlin.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hellokotlin.R
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.ImageUrl
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.data.util.ImageUtil
import com.example.hellokotlin.databinding.MainFragmentBinding
import com.example.hellokotlin.ui.adapter.MovieAdapter
import com.example.hellokotlin.ui.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewBinding: MainFragmentBinding

    @Inject
    lateinit var  imageUtil:ImageUtil

    private lateinit var rvUsersAdapter:UsersAdapter

    private lateinit var rvMoviewAdaper:MovieAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = MainFragmentBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.movies.observe(this, Observer {
            renderViewMovies(it)
        })
        viewModel.users.observe(this, Observer {
            renderViewUsers(it)
        })
        rvUsersAdapter = UsersAdapter(imageUtil)
        viewBinding.rvUsers.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        viewBinding.rvUsers.adapter = rvUsersAdapter

        rvMoviewAdaper = MovieAdapter(imageUtil)
        viewBinding.rvMovies.layoutManager = GridLayoutManager(context,3)
        viewBinding.rvMovies.adapter = rvMoviewAdaper

        viewModel.refresh()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun renderViewUsers(resource: Resource<List<User>>) {
        Log.d("TAG","render views")
        when(resource){

            is Resource.Success ->{
                resource.data?.let {
                    rvUsersAdapter.add(it) }
            }
        }
    }
    private fun renderViewMovies(resource: Resource<List<Movie>>) {
        when(resource){
            is Resource.Success ->{
                resource.data?.let { rvMoviewAdaper.add(it) }
            }
        }
    }
}