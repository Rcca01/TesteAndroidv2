package com.example.accentureteste.ui.statements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accentureteste.R
import com.example.accentureteste.data.model.ResponseStatements
import kotlinx.android.synthetic.main.statement_item.view.*
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class StatementsAdapter(val statements: List<ResponseStatements.Statements>):
    RecyclerView.Adapter<StatementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.statement_item, parent, false)
        return StatementViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statements.size
    }

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        return holder.bind(statements[position])
    }
}

class StatementViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    private val titulo = itemView.titulo
    private val dataStatement = itemView.data
    private val descricaoStatement = itemView.descricao
    private val valorStatement = itemView.valor

    fun bind(statement: ResponseStatements.Statements) {
        titulo.text = statement.title
        dataStatement.text = this.converteDataPtbr(statement.date)
        valorStatement.text = this.converteMoedaReal(statement.value)
        descricaoStatement.text = statement.desc
    }

    private fun converteMoedaReal(valor: Double): String {
        val ptBr = Locale("pt", "BR")
        return NumberFormat.getCurrencyInstance(ptBr).format(valor)
    }

    private fun converteDataPtbr(data: String): String {
        val formatoDataDesejadoUS = SimpleDateFormat("yyyy-mm-dd")
        val formatoDataDesejadoBR = SimpleDateFormat("dd/mm/yyyy")
        val dataUS = formatoDataDesejadoUS.parse(data)
        dataUS?.let {
            return formatoDataDesejadoBR.format(dataUS)
        } ?: run {
            return ""
        }
    }
}