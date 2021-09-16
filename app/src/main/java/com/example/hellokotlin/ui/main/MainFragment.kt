package com.example.hellokotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.databinding.MainFragmentBinding
import com.example.hellokotlin.ui.adapter.MovieAdapter
import com.example.hellokotlin.ui.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewBinding: MainFragmentBinding

    private var rvUsersAdapter = UsersAdapter()

    private var rvMoviewAdaper = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = MainFragmentBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.movies.observe(viewLifecycleOwner, {
            renderViewMovies(it)
        })
        viewModel.users.observe(viewLifecycleOwner, {
            renderViewUsers(it)
        })

        viewBinding.rvUsers.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        viewBinding.rvUsers.adapter = rvUsersAdapter

        viewBinding.rvMovies.layoutManager = GridLayoutManager(context,3)
        viewBinding.rvMovies.adapter = rvMoviewAdaper

        viewModel.refresh()

    }

    private fun renderViewUsers(resource: Resource<List<User>>) {
        when(resource){
            is Resource.Success ->{
                resource.data?.let {
                    rvUsersAdapter.add(it) }
            }
            is Resource.Error ->{}
            is Resource.Loading -> {}
        }
    }
    private fun renderViewMovies(resource: Resource<List<Movie>>) {
        when(resource){
            is Resource.Success ->{
                resource.data?.let { rvMoviewAdaper.add(it) }
            }
            is Resource.Error -> TODO()
            is Resource.Loading -> TODO()
        }
    }
}