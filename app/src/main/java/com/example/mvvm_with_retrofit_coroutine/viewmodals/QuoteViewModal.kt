package com.example.mvvm_with_retrofit_coroutine.viewmodals

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.net.NetworkInfo
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import com.example.mvvm_with_retrofit_coroutine.api.QuoteApiInterface
import com.example.mvvm_with_retrofit_coroutine.modals.QuoteList
import com.example.mvvm_with_retrofit_coroutine.modals.Result
import com.example.mvvm_with_retrofit_coroutine.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteViewModal(private val quotesRepository: QuotesRepository) : ViewModel()
{
    private val quoteliveData = MutableLiveData<QuoteList>()
    val quotes : LiveData<QuoteList> = quoteliveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = quotesRepository.getQuote(1)
            val addData = result.body()
            if (addData != null) {
                quoteliveData.postValue(result.body())
            }
        }
    }
}