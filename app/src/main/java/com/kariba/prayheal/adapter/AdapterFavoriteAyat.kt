package com.kariba.prayheal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemFavoriteAyatBinding
import com.kariba.prayheal.interfaces.OnCarouselClickListener
import com.kariba.prayheal.interfaces.onAyatClickListener
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.CarouselResponse

class AdapterFavoriteAyat(private val context: Context) : RecyclerView.Adapter<AdapterFavoriteAyat.FavoriteAyatViewHolder>() {

    var ayatList : ArrayList<AyahsData> = ArrayList()

    fun setAyatData(ayatList : ArrayList<AyahsData>){
        this.ayatList = ayatList
    }

    var ayatClick : onAyatClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAyatViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemFavoriteAyatBinding>(LayoutInflater.from(context), R.layout.item_favorite_ayat, parent, false)

        return FavoriteAyatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteAyatViewHolder, position: Int) {
        ayatClick?.let { holder.bindView(context, ayatList[position], it) }
    }

    override fun getItemCount(): Int {
        return return if(ayatList.size == 0) ayatList.size else 4
    }

    class FavoriteAyatViewHolder(private val itemBinding: ItemFavoriteAyatBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bindView(
            context: Context,
            ayahsData: AyahsData,
            ayatClick: onAyatClickListener
        ) {
            itemBinding.ayatData = ayahsData
            itemBinding.executePendingBindings()

            itemBinding.cardViewAyat.setOnClickListener {
                ayatClick.ayatOnClick(it, adapterPosition)
            }

        }
    }

}