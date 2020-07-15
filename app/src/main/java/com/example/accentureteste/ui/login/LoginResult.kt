package com.example.accentureteste.ui.login

import com.example.accentureteste.data.model.ResponseLogin

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: ResponseLogin.UserAccount? = null,
        val error: Int? = null
)
