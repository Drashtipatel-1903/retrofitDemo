package com.example.mvvm_with_retrofit_coroutine.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_with_retrofit_coroutine.api.QuoteApiInterface
import com.example.mvvm_with_retrofit_coroutine.modals.QuoteList

class QuotesRepository(private val quoteApiInterface: QuoteApiInterface)
{
//    private val quoteliveData = MutableLiveData<QuoteList>()
//
//    val quotes : LiveData<QuoteList>
//    get() = quoteliveData

    suspend fun getQuote(page : Int) = quoteApiInterface.getQuotes(page)

//    {
//        val result = quoteApiInterface.getQuotes(page)
//        if(result?.body()!=null)
//        {
//            quoteliveData.postValue(result.body())
//        }
//    }
}