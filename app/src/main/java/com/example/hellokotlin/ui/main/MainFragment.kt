package com.example.hellokotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewBinding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = MainFragmentBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.button.setOnClickListener(View.OnClickListener {
            showProgress(true)
            viewModel.login(viewBinding.username.text.toString(), viewBinding.password.text.toString())
        })
    }

    private fun showProgress(isShoing: Boolean) {
        if(isShoing){
            viewBinding.group.visibility = ViewGroup.GONE
            viewBinding.progress.visibility = ViewGroup.VISIBLE
        }else{
            viewBinding.group.visibility = ViewGroup.VISIBLE
            viewBinding.progress.visibility = ViewGroup.GONE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.user.observe(this,  Observer{ user->
            showProgress(false)
            if(user == null){
                showMessage("Invalid username or password")
            }else {
                showMessage("User is ${user.name}")
            }
        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

}