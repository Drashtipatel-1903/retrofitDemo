package com.example.mvvm_with_retrofit_coroutine.api

import com.example.mvvm_with_retrofit_coroutine.modals.QuoteList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApiInterface
{
    @GET("quotes")
    suspend fun getQuotes(@Query("page") page : Int) : Response<QuoteList>
}