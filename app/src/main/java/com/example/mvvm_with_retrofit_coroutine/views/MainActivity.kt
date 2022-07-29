package com.example.mvvm_with_retrofit_coroutine.views

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_with_retrofit_coroutine.R
import com.example.mvvm_with_retrofit_coroutine.adapters.MyAdapter
import com.example.mvvm_with_retrofit_coroutine.api.QuoteService
import com.example.mvvm_with_retrofit_coroutine.databinding.ActivityMainBinding
import com.example.mvvm_with_retrofit_coroutine.modals.QuoteList
import com.example.mvvm_with_retrofit_coroutine.modals.Result
import com.example.mvvm_with_retrofit_coroutine.repository.QuotesRepository
import com.example.mvvm_with_retrofit_coroutine.viewmodals.QuoteViewModal
import com.example.mvvm_with_retrofit_coroutine.viewmodals.QuoteViewModalFactory
import java.lang.Exception
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    lateinit var quoteViewModal: QuoteViewModal
    private var dataList: MutableList<Result> = ArrayList()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val rev = binding?.recyclerview
        val quoteService = QuoteService.getInstance()

        val repository = QuotesRepository(quoteService)

        binding?.progressbar?.visibility = View.VISIBLE

        if (IsCheckInternetConnection()) {
            quoteViewModal = ViewModelProvider(
                this,
                QuoteViewModalFactory(repository)
            ).get(QuoteViewModal::class.java)
            quoteViewModal.quotes.observe(this, Observer { it ->
                Log.d("tag", it.results.toString())
                dataList.addAll(it.results)
                rev?.layoutManager = LinearLayoutManager(this)
                var adapter = MyAdapter(dataList)
                rev?.adapter = adapter
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Connection Available", Toast.LENGTH_SHORT).show()
                binding?.progressbar?.visibility = View.GONE

            })
        } else {
            binding?.progressbar?.visibility = View.GONE
            Toast.makeText(this, "Connection Not Available", Toast.LENGTH_LONG).show()
        }

    }

//    private fun checkForInternet(): Boolean {
//
//        // register activity with the connectivity manager service
//        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        // if the android version is equal to M
//        // or greater we need to use the
//        // NetworkCapabilities to check what type of
//        // network has the internet connection
//
//        // Returns a Network object corresponding to
//        // the currently active default data network.
//        val network = connectivityManager.activeNetwork ?: return false
//
//        // Representation of the capabilities of an active network.
//        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
//
//        return when {
//            // Indicates this network uses a Wi-Fi transport,
//            // or WiFi has network connectivity
//            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//
//            // Indicates this network uses a Cellular transport. or
//            // Cellular has network connectivity
//            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//
//            // else return false
//            else -> false
//        }
//    }

    private fun IsCheckInternetConnection(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }

    }


}