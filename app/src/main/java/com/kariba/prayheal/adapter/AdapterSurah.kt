package com.kariba.prayheal.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemSurahBinding
import com.kariba.prayheal.interfaces.FavoriteSurahClickListener
import com.kariba.prayheal.interfaces.OnCarouselClickListener
import com.kariba.prayheal.interfaces.OnItemClickListener
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.models.SurahData
import javax.inject.Inject

class AdapterSurah @Inject constructor(private val context: Context) : RecyclerView.Adapter<AdapterSurah.SurahViewHolder>() {

    var surahDataList : ArrayList<SurahData> = ArrayList()

    fun setSurahData(carouselDataList : ArrayList<SurahData>){
        this.surahDataList = carouselDataList
    }

    var onItemClickListener : OnItemClickListener? = null
    var onFavoriteClickListener : FavoriteSurahClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemSurahBinding>(LayoutInflater.from(context), R.layout.item_surah, parent, false)

        return SurahViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        holder.bindView(context, onItemClickListener, onFavoriteClickListener, surahDataList[position])
    }

    override fun getItemCount(): Int {
        return surahDataList.size
    }

    class SurahViewHolder(private val itemBinding: ItemSurahBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bindView(
            context: Context,
            onItemClickListener: OnItemClickListener?,
            onFavoriteClickListener: FavoriteSurahClickListener?,
            surahData: SurahData
        ) {

            itemBinding.surahItem = surahData
            itemBinding.executePendingBindings()


            itemBinding.layoutItem.setOnClickListener {
                onItemClickListener?.onClick(it, adapterPosition)
            }

            if(surahData.englishName?.toLowerCase()?.contains("al-faatiha") == true){
                /*Glide.with(context)
                    .load(ContextCompat.getDrawable(context, R.drawable.ic_heart_two))
                    .error(ContextCompat.getDrawable(context, R.drawable.ic_heart_one))
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_heart_one))
                    .into(itemBinding.imageViewFavorite)*/

                itemBinding.imageViewFavorite.setColorFilter(ContextCompat.getColor(context, R.color.red_apple))
                return
            }

            itemBinding.imageViewFavorite.setOnClickListener {
                onFavoriteClickListener?.favoriteSurahClick(it, adapterPosition)
            }

        }

    }

}