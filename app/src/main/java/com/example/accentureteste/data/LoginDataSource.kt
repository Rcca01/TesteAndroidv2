package com.example.accentureteste.data

import com.example.accentureteste.data.model.LoggedInUser
import com.example.accentureteste.retrofit.BankHttpApi
import retrofit2.Call
import java.io.IOException
import retrofit2.Callback
import retrofit2.Response

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource (val bankHttpApi: BankHttpApi) {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

