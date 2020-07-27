package com.example.accentureteste.ui.statements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.accentureteste.R
import com.example.accentureteste.data.model.ResponseStatements
import com.example.accentureteste.retrofit.BankHttpApi
import com.example.accentureteste.ui.login.StatementResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StatementsViewModel: ViewModel() {


    private val _statementResult = MutableLiveData<StatementResult>()
    val statementResult: LiveData<StatementResult> = _statementResult

    fun listStatements(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bank-app-test.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val bankHttpApi = retrofit.create(BankHttpApi::class.java)
        val call = bankHttpApi.getStatements(id)
        call.enqueue(object: Callback<ResponseStatements> {
            override fun onResponse(
                call: Call<ResponseStatements>,
                response: Response<ResponseStatements>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        _statementResult.value = StatementResult(
                            success = it.data
                        )
                    }
                } else {
                    _statementResult.value = StatementResult(
                        error = R.string.statement_failed
                    )
                }
            }

            override fun onFailure(call: Call<ResponseStatements>, t: Throwable) {
                _statementResult.value = StatementResult(
                    error = R.string.statement_failed
                )
            }
        })
    }

}