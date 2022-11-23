package com.kariba.prayheal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kariba.prayheal.R
import com.kariba.prayheal.databinding.ItemSurahBinding
import com.kariba.prayheal.interfaces.OnItemClickListener
import com.kariba.prayheal.models.CarouselResponse
import javax.inject.Inject

class AdapterSurah @Inject constructor(private val context: Context) : RecyclerView.Adapter<AdapterSurah.SurahViewHolder>() {

    var surahDataList : ArrayList<CarouselResponse.CarouselData.SurahData> = ArrayList()

    fun setSurahData(carouselDataList : ArrayList<CarouselResponse.CarouselData.SurahData>){
        this.surahDataList = carouselDataList
    }

    var onItemClickListener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemSurahBinding>(LayoutInflater.from(context), R.layout.item_surah, parent, false)

        return SurahViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        holder.bindView(context, onItemClickListener, surahDataList[position])
    }

    override fun getItemCount(): Int {
        return surahDataList.size
    }

    class SurahViewHolder(private val itemBinding: ItemSurahBinding) : RecyclerView.ViewHolder(itemBinding.root){

        fun bindView(
            context: Context,
            onItemClickListener: OnItemClickListener?,
            surahData: CarouselResponse.CarouselData.SurahData
        ) {

            itemBinding.surahItem = surahData
            itemBinding.executePendingBindings()


            itemBinding.layoutItem.setOnClickListener {
                onItemClickListener?.onClick(it, adapterPosition)
            }

        }

    }

}