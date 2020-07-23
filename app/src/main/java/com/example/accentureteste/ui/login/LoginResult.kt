package com.example.accentureteste.ui.login

import com.example.accentureteste.data.model.ResponseLogin
import com.example.accentureteste.data.model.ResponseStatements

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: ResponseLogin.UserAccount? = null,
        val error: Int? = null
)

data class StatementResult(
        val success: List<ResponseStatements.Statements>? = null,
        val error: Int? = null
)
