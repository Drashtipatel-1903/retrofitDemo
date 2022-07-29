package com.example.mvvm_with_retrofit_coroutine.api

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class QuoteService {
    companion object {
        private var INSTANCE: QuoteApiInterface? = null
        private var retrofitInstance: Retrofit? = null
        private const val BASE_URL = "https://quotable.io/"

        @Synchronized
        fun getInstance(): QuoteApiInterface {
            if (INSTANCE == null) {
                INSTANCE = retrofit().create(QuoteApiInterface::class.java)
            }
            return INSTANCE as QuoteApiInterface
        }

        private fun retrofit(): Retrofit {
            if (retrofitInstance == null) {
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }
            return retrofitInstance as Retrofit
        }

        @SuppressLint("ServiceCast")
        private fun checkForInternet(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val network = connectivityManager.activeNetwork ?: return false

                // Representation of the capabilities of an active network.
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            } else {
                // if the android version is below M
                @Suppress("DEPRECATION") val networkInfo =
                    connectivityManager.activeNetworkInfo ?: return false
                @Suppress("DEPRECATION")
                return networkInfo.isConnected
            }
        }
    }
}