package com.kariba.prayheal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemAyahBinding
import com.kariba.prayheal.models.CarouselResponse

class AdapterAyat(private val context: Context): RecyclerView.Adapter<AdapterAyat.AyatViewHolder>() {

    var ayahList : ArrayList<CarouselResponse.CarouselData.SurahData.AyahsData> = ArrayList()

    fun setAyahDataList(ayahList : ArrayList<CarouselResponse.CarouselData.SurahData.AyahsData>){
        this.ayahList = ayahList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemAyahBinding>(LayoutInflater.from(context), R.layout.item_ayah, parent, false)

        return AyatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        holder.bindView(context, ayahList[position])
    }

    override fun getItemCount(): Int {
        return ayahList.size
    }

    class AyatViewHolder(private val itemBinding: ItemAyahBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bindView(
            context: Context,
            ayahsData: CarouselResponse.CarouselData.SurahData.AyahsData
        ) {

            itemBinding.ayahResponse = ayahsData
            itemBinding.executePendingBindings()

        }

    }
}