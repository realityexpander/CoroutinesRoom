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

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val db by lazy { UserDatabase(getApplication()).userDao()}

    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun  signup(username: String, password: String, info: String) {
         coroutineScope.launch {
             val user = db.getUser(username)

             // Found a user?
             if (user != null) {
                 withContext(Dispatchers.Main) {
                     error.value = "User already exists!"
                 }
             } else {
                 val user = User(username, passwordHash = password.hashCode(), info = info)
                 val userId = db.insertUser(user)
                 user.id = userId
                 LoginState.login(user)
                 withContext(Dispatchers.Main) {
                     signupComplete.value = true
                 }
             }
         }
    }

}