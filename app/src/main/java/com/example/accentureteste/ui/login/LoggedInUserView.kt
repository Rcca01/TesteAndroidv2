package com.example.accentureteste.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
        val displayName: String,
        val user: String,
        val password: String
        //... other data fields that may be accessible to the UI
)
