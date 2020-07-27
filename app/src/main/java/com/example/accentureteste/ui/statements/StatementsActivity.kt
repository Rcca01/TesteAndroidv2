package com.example.accentureteste.ui.statements

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accentureteste.R
import com.example.accentureteste.data.model.ResponseLogin
import kotlinx.android.synthetic.main.activity_statements.*
import java.text.NumberFormat
import java.util.*

class StatementsActivity : AppCompatActivity() {

    private lateinit var statementsViewModel: StatementsViewModel
    private lateinit var statementAdapter: StatementsAdapter
    private var userAccount: ResponseLogin.UserAccount? = null
    lateinit var loading: ProgressBar
    lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statements)
        this.loading = findViewById(R.id.loading)
        this.btnLogout = findViewById(R.id.btnLogout)

        statementsViewModel =  ViewModelProviders.of(this, StatementsViewModelFactory())
            .get(StatementsViewModel::class.java)
        val ptBr = Locale("pt", "BR")
        val bundle = intent.getBundleExtra("USER_ACCOUNT")
        userAccount = bundle?.getParcelable("user")
        userAccount?.let {
            nome.text = it.name
            conta.text = it.bankAccount+ " / " +it.agency
            saldo.text = NumberFormat.getCurrencyInstance(ptBr).format(it.balance)
        }

        getList()
        observerListStatements()

        this.btnLogout.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("loginBank", Context.MODE_PRIVATE)
            val editSharedPreferences = sharedPreferences.edit()
            editSharedPreferences.putString("user", "")
            editSharedPreferences.putString("password", "")
            editSharedPreferences.apply()
            finishAffinity()
        }
    }

    private fun getList() {
        statementsViewModel.listStatements(userAccount?.userId ?: 0)
    }

    private fun observerListStatements() {
        statementsViewModel.statementResult.observe(this@StatementsActivity, Observer {
            loading.visibility = View.GONE
            it.success?.let { list ->
                recyclerView.apply {
                    setHasFixedSize(true)
                    val layout =  LinearLayoutManager(this@StatementsActivity)
                    layout.orientation = LinearLayoutManager.VERTICAL
                    layoutManager = layout
                    adapter = StatementsAdapter(list)
                }
            }
        })
    }


}
