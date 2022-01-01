package com.realityexpander.coroutinesroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.realityexpander.coroutinesroom.model.LoginState
import com.realityexpander.coroutinesroom.model.User
import com.realityexpander.coroutinesroom.model.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao()}

    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(username: String, password: String) {
        coroutineScope.launch {
            val user = db.getUser(username)

            // Valid password?
            if (user != null && user.passwordHash == password.hashCode()) {
                LoginState.login(user)
                withContext(Dispatchers.Main) {
                    loginComplete.value = true
                }
            } else {
                // User not found
                withContext(Dispatchers.Main) {
                    error.value = "User not found!"
                }
            }
        }
    }
}