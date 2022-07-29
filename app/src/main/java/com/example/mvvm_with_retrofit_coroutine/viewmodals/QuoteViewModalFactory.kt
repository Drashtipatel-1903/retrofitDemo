package com.example.mvvm_with_retrofit_coroutine.viewmodals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_with_retrofit_coroutine.repository.QuotesRepository

class QuoteViewModalFactory(private val quotesRepository: QuotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuoteViewModal(quotesRepository) as T
    }
}