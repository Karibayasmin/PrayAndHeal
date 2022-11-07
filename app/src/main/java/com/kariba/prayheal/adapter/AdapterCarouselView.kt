package com.kariba.prayheal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemCarouselViewBinding
import com.kariba.prayheal.interfaces.OnClickListener
import com.kariba.prayheal.models.CarouselResponse

class AdapterCarouselView(private val context: Context, private var itemClick: OnClickListener) : RecyclerView.Adapter<AdapterCarouselView.AdapterCarouselViewHolder>() {

    var carouselDataList : ArrayList<CarouselResponse> = ArrayList()

    fun setCarouselList(list : ArrayList<CarouselResponse>){
        carouselDataList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCarouselViewHolder {
        var itemBinding = DataBindingUtil.inflate<ItemCarouselViewBinding>(LayoutInflater.from(context), R.layout.item_carousel_view, parent, false )

        return AdapterCarouselViewHolder (itemBinding)
    }

    override fun onBindViewHolder(holder: AdapterCarouselViewHolder, position: Int) {
        holder.bindView(context, carouselDataList[position], itemClick, position)
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

    class AdapterCarouselViewHolder(private var itemBinding: ItemCarouselViewBinding) : RecyclerView.ViewHolder(itemBinding.root){
          fun bindView(context: Context, data: CarouselResponse, itemClick: OnClickListener, position: Int) {

              itemBinding.textViewName.text = data.name

              itemBinding.layoutItem.setOnClickListener {
                  itemClick.onClick(it, adapterPosition)
              }
              itemBinding.executePendingBindings()

          }
    }

}