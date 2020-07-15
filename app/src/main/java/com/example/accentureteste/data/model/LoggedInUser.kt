package com.example.accentureteste.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
class LoggedInUser(
        val user: String,
        val password: String
)
