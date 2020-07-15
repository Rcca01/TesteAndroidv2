package com.example.accentureteste.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
        val user: String,
        val password: String
)
