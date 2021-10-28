package com.example.hellokotlin.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hellokotlin.EventObserver
import com.example.hellokotlin.data.Resource
import com.example.hellokotlin.data.model.User
import com.example.hellokotlin.databinding.LoginFragmentBinding
import com.example.hellokotlin.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
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

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoading(it)
        })
        viewModel.eventError.observe(viewLifecycleOwner,EventObserver{
            showMessage(it)
        })

        viewModel.eventLoggedIn.observe(viewLifecycleOwner,EventObserver{
            gotoMain()
        })

        viewBinding.button.setOnClickListener(View.OnClickListener {
            hideKeyboard()
            viewModel.login(viewBinding.username.text.toString(), viewBinding.password.text.toString())
        })

        viewModel.load()
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    private fun gotoMain() {
        val intent = Intent(context, MainActivity::class.java)
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

    private fun showMessage(@StringRes resId:Int) {
        Snackbar.make(viewBinding.root,resId,Snackbar.LENGTH_LONG).show()
    }

}