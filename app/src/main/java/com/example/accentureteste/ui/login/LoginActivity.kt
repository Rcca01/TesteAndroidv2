package com.example.accentureteste.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.example.accentureteste.R
import com.example.accentureteste.ui.statements.StatementsActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var login: Button
    lateinit var loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        this.username = findViewById(R.id.username)
        this.password = findViewById(R.id.password)
        this.login = findViewById(R.id.login)
        this.loading = findViewById(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        this.setConfigInputsLogin()
        this.observeStatusLogin()
        this.observeResultLogin()
        this.setValuesInput()
    }

    private fun setValuesInput() {
        val sharedPreferences = this.getSharedPreferences("loginBank", Context.MODE_PRIVATE)
        this.username.setText(sharedPreferences.getString("user", ""))
        this.password.setText(sharedPreferences.getString("password", ""))
    }

    private fun observeStatusLogin() {
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })
    }

    private fun observeResultLogin() {
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            loginResult.success?.let {
                val intent = Intent(this, StatementsActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("user", loginResult.success)
                intent.putExtra("USER_ACCOUNT", bundle)
                this.saveDataLogin(username.text.toString(), password.text.toString())
                startActivity(intent)
            }
        })
    }

    private fun saveDataLogin(username: String, password: String) {
        val sharedPreferences = this.getSharedPreferences("loginBank", Context.MODE_PRIVATE)
        val editSharedPreferences = sharedPreferences.edit()
        editSharedPreferences.putString("user", username)
        editSharedPreferences.putString("password", password)
        editSharedPreferences.apply()
    }

    private fun setConfigInputsLogin() {
        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
