package com.example.mvvm_with_retrofit_coroutine.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_with_retrofit_coroutine.R
import com.example.mvvm_with_retrofit_coroutine.databinding.RawLayoutBinding
import com.example.mvvm_with_retrofit_coroutine.modals.QuoteList
import com.example.mvvm_with_retrofit_coroutine.modals.Result
import com.google.android.material.textview.MaterialTextView

class MyAdapter(private var allData:MutableList<Result>)
    : RecyclerView.Adapter<MyAdapter.ViewHolder>()
{
    class ViewHolder( private var binding : RawLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val textview1 = binding.tv1
        val textview2 = binding.tv2
        val textview3 = binding.tv3
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RawLayoutBinding.inflate(LayoutInflater.from(parent.context)  , parent ,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myData = allData[position]
        holder.textview1.text = myData.author
        holder.textview2.text = myData.content
        holder.textview3.text = myData.length.toString()
    }

    override fun getItemCount(): Int {
        return allData.size
    }

}