package com.kariba.prayheal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemAyahBinding
import com.kariba.prayheal.interfaces.OnCarouselClickListener
import com.kariba.prayheal.models.AyahsData

class AdapterAyat(private val context: Context): RecyclerView.Adapter<AdapterAyat.AyatViewHolder>() {

    var ayahList : ArrayList<AyahsData> = ArrayList()

    fun setAyahDataList(ayahList : ArrayList<AyahsData>){
        this.ayahList = ayahList
    }

    var onFavoriteAyahClick : OnCarouselClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemAyahBinding>(LayoutInflater.from(context), R.layout.item_ayah, parent, false)

        return AyatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        onFavoriteAyahClick?.let { holder.bindView(context, ayahList[position], it) }
    }

    override fun getItemCount(): Int {
        return ayahList.size
    }

    class AyatViewHolder(private val itemBinding: ItemAyahBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bindView(
            context: Context,
            ayahsData: AyahsData,
            onFavoriteAyahClick: OnCarouselClickListener
        ) {

            itemBinding.ayahResponse = ayahsData
            itemBinding.executePendingBindings()

            itemBinding.imageViewFavorite.setOnClickListener {
                onFavoriteAyahClick.onClick(it, adapterPosition)
            }

            if(ayahsData.isFavorite == true){
                itemBinding.imageViewFavorite.setColorFilter(ContextCompat.getColor(context, R.color.red_apple))
                return
            }else{
                itemBinding.imageViewFavorite.setColorFilter(ContextCompat.getColor(context, R.color.blue))
                return
            }

        }

    }
}