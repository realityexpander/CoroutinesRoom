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
import com.realityexpander.coroutinesroom.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signoutBtn.setOnClickListener { onSignout() }
        deleteUserBtn.setOnClickListener { onDelete() }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signout.observe(this, Observer {
            Toast.makeText(activity, "Logout complete", Toast.LENGTH_LONG).show()
            val action = MainFragmentDirections.actionGoToSignup()
            Navigation.findNavController(signoutBtn).navigate(action)
        })
        viewModel.userDeleted.observe(this, Observer {
            Toast.makeText(activity, "User deleted and logged out", Toast.LENGTH_LONG).show()
            val action = MainFragmentDirections.actionGoToLogin()
            Navigation.findNavController(deleteUserBtn).navigate(action)
        })
    }

    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun onDelete() {
        viewModel.onDeleteUser()
    }

}
