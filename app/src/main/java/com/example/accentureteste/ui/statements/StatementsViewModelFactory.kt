package com.example.accentureteste.ui.statements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StatementsViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatementsViewModel::class.java)) {
            return StatementsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}