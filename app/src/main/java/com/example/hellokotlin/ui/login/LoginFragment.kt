package com.example.hellokotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.databinding.LoginFragmentBinding
import com.example.hellokotlin.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewBinding: LoginFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewBinding = LoginFragmentBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.data.observe(viewLifecycleOwner,  Observer{ r->
            renderView(r)
        })

        viewBinding.button.setOnClickListener(View.OnClickListener {
            showLoading(true)
            viewModel.login(viewBinding.username.text.toString(), viewBinding.password.text.toString())
        })
    }

    private fun renderView(resource: Resource<User>?) {

        when(resource){
            is Resource.Loading -> {showLoading(true)}
            is Resource.Success -> {
                showLoading(false)
                if(resource.data == null){
                    showMessage("User not found")
                }else {
                    gotoMain()
                }
            }
            is Resource.Error -> {
                showLoading(false)
                showMessage("Some error occurs (${resource.error})")
            }
        }

    }

    private fun gotoMain() {
        val intent = Intent(context,MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun showLoading(isShoing: Boolean) {
        if(isShoing){
            viewBinding.group.visibility = ViewGroup.GONE
            viewBinding.progress.visibility = ViewGroup.VISIBLE
        }else{
            viewBinding.group.visibility = ViewGroup.VISIBLE
            viewBinding.progress.visibility = ViewGroup.GONE
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

}