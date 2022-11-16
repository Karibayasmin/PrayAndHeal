package com.kariba.prayheal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemFavoriteAyatBinding
import com.kariba.prayheal.interfaces.OnClickListener
import com.kariba.prayheal.models.CarouselResponse

class AdapterFavoriteAyat(private val context: Context, private val ayatClick : OnClickListener) : RecyclerView.Adapter<AdapterFavoriteAyat.FavoriteAyatViewHolder>() {

    var ayatList : ArrayList<CarouselResponse.CarouselData.SurahData.AyahsData> = ArrayList()

    fun setAyatData(ayatList : ArrayList<CarouselResponse.CarouselData.SurahData.AyahsData>){
        this.ayatList = ayatList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAyatViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemFavoriteAyatBinding>(LayoutInflater.from(context), R.layout.item_favorite_ayat, parent, false)

        return FavoriteAyatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteAyatViewHolder, position: Int) {
        holder.bindView(context, ayatList[position], ayatClick)
    }

    override fun getItemCount(): Int {
        return return if(ayatList.size == 0) ayatList.size else 4
    }

    class FavoriteAyatViewHolder(private val itemBinding: ItemFavoriteAyatBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bindView(
            context: Context,
            ayahsData: CarouselResponse.CarouselData.SurahData.AyahsData,
            ayatClick: OnClickListener
        ) {
            itemBinding.ayatData = ayahsData
            itemBinding.executePendingBindings()

            itemBinding.cardViewAyat.setOnClickListener {
                ayatClick.onClick(it, adapterPosition)
            }

        }
    }

}