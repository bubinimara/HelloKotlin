package com.example.hellokotlin.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hellokotlin.R
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.databinding.MainFragmentBinding
import com.example.hellokotlin.ui.adapter.UsersAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewBinding: MainFragmentBinding

    private val rvUsersAdapter = UsersAdapter()

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
        viewBinding.rvUsers.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        viewBinding.rvUsers.adapter = rvUsersAdapter
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
                    Log.d("TAG","adding users")
                    rvUsersAdapter.add(it) }
            }
        }
    }
    private fun renderViewMovies(resource: Resource<List<Movie>>) {
        when(resource){
            is Resource.Success ->{}
        }
    }
}