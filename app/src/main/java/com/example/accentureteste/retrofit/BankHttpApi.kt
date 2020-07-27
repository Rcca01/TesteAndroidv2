package com.example.accentureteste.retrofit

import com.example.accentureteste.data.model.ResponseLogin
import com.example.accentureteste.data.model.ResponseStatements
import retrofit2.Call
import retrofit2.http.*


interface BankHttpApi {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("user") user: String,
              @Field("password") password: String): Call<ResponseLogin>

    @GET("statements/{id}")
    fun getStatements(@Path("id") id: Int): Call<ResponseStatements>
}