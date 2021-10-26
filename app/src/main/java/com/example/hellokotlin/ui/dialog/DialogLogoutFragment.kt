package com.example.hellokotlin.ui.dialog

import android.app.Dialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.hellokotlin.EventObserver
import com.example.hellokotlin.R
import com.example.hellokotlin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogLogoutFragment : DialogFragment() {

    companion object {
        fun newInstance() = DialogLogoutFragment()
    }

    private lateinit var viewModel: DialogLogoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DialogLogoutViewModel::class.java)

        viewModel.eventLogout.observe(this, EventObserver{
            goToLogin()
        })

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_logout_title)
            .setPositiveButton(R.string.btn_logout) { a, b ->
                viewModel.logout()
            }
            .setNegativeButton(R.string.btn_cancel,null)
            .create()
    }

    private fun goToLogin() {
        val intent= Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity?.finish()
    }

}