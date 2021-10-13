package com.example.hellokotlin.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hellokotlin.R
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.Movie
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.databinding.MainFragmentBinding
import com.example.hellokotlin.ui.adapter.AdapterClickListener
import com.example.hellokotlin.ui.adapter.MovieAdapter
import com.example.hellokotlin.ui.adapter.UsersAdapter
import com.example.hellokotlin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var viewBinding: MainFragmentBinding

    private var rvUsersAdapter = UsersAdapter()

    private var rvMoviewAdaper = MovieAdapter(object : AdapterClickListener<Movie> {
        override fun onItemClicked(item: Movie) {
            val action =
                MainFragmentDirections.movieDetailAction(item.id)
            findNavController().navigate(action)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout ->{
                // todo: show dialog
                if(true){
                viewModel.rateTest()
                    return true
                }
                viewModel.logout()
                goToLogin()
                return true
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }

    }

    private fun goToLogin() {
        // @Todo:check if resumed
        val intent= Intent(context,LoginActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun renderViewMovies(resource: Resource<List<Movie>>) {
        when(resource){
            is Resource.Success ->{
                resource.data?.let { rvMoviewAdaper.set(it) }
            }
            is Resource.Error -> showMessage("Error ${resource.error}")
            is Resource.Loading -> showMessage("Loading")
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}

