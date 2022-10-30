package com.kariba.prayheal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemCarouselViewBinding

class AdapterCarouselView(private val context: Context) : RecyclerView.Adapter<AdapterCarouselView.AdapterCarouselViewHolder>() {

    var carouselDataList : ArrayList<String> = ArrayList()

    fun setCarouselList(list : ArrayList<String>){
        carouselDataList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCarouselViewHolder {
        var itemBinding = DataBindingUtil.inflate<ItemCarouselViewBinding>(LayoutInflater.from(context), R.layout.item_carousel_view, parent, false )

        return AdapterCarouselViewHolder (itemBinding)
    }

    override fun onBindViewHolder(holder: AdapterCarouselViewHolder, position: Int) {
        holder.bindView(carouselDataList[position])
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

    class AdapterCarouselViewHolder(private var itemBinding: ItemCarouselViewBinding) : RecyclerView.ViewHolder(itemBinding.root){
          fun bindView(data: String) {

              itemBinding.textViewName.text = data
              itemBinding.executePendingBindings()

          }
    }

}