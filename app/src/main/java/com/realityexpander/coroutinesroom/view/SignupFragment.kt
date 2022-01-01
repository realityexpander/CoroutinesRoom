package com.realityexpander.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.realityexpander.coroutinesroom.R
import com.realityexpander.coroutinesroom.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signupBtn.setOnClickListener { onSignup(it) }
        gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(this, Observer { isComplete ->
            Toast.makeText(activity, "Signup complete", Toast.LENGTH_LONG).show()
            val action = SignupFragmentDirections.actionGoToMain()
            Navigation.findNavController(signupUsername).navigate(action)
        })

        viewModel.error.observe(this, Observer { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

    private fun onSignup(v: View){

        val username = signupUsername.text.toString().trim()
        val password = signupPassword.text.toString().trim()
        val info = otherInfo.text.toString().trim()

        if (username.isEmpty() || password.isEmpty() || info.isBlank()) {
            Toast.makeText(activity, "Please fill all fields", Toast.LENGTH_LONG ).show()
        } else {
            viewModel.signup(username, password, info)
        }
    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }
}
