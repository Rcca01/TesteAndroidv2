package com.example.accentureteste.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.example.accentureteste.R
import com.example.accentureteste.data.model.ResponseLogin
import com.example.accentureteste.retrofit.BankHttpApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class LoginViewModel: ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

     fun login(username: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bank-app-test.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bankHttpApi = retrofit.create(BankHttpApi::class.java)
         val call = bankHttpApi.login(username, password)
         call.enqueue(object: Callback<ResponseLogin> {
             override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                 if (response.isSuccessful) {
                     response.body()?.let {
                         _loginResult.value = LoginResult(success = it.data)
                     }
                 } else {
                     _loginResult.value = LoginResult(error = R.string.login_failed)
                 }
             }
             override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                 _loginResult.value = LoginResult(error = R.string.login_failed)
             }
         })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}".toRegex().containsMatchIn(username)
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        val regexValidateCapitalLetter = Regex("[A-Z]+")
        val regexValidateNumber = Regex("[0-9]+")
        val regexValidateSpecial = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+")
        return regexValidateCapitalLetter.containsMatchIn(password) &&
                regexValidateNumber.containsMatchIn(password) &&
                regexValidateSpecial.containsMatchIn(password)
    }
}
