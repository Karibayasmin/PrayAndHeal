package com.kariba.prayheal.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kariba.prayheal.R
import com.kariba.prayheal.adapter.AdapterAyat
import com.kariba.prayheal.databinding.FragmentCarouselItemBinding
import com.kariba.prayheal.localDatabase.LocalDatabase
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.CarouselResponse
import kotlinx.android.synthetic.main.fragment_carousel_item.*
import java.lang.reflect.Type

class CarouselItemFragment : Fragment() {

    var carouselItem : CarouselResponse.CarouselData.SurahData = CarouselResponse.CarouselData.SurahData()
    var ayahList: ArrayList<AyahsData> = ArrayList()

    lateinit var ayahAdapter : AdapterAyat

    lateinit var localDatabase : LocalDatabase

    var surahName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCarouselItemBinding>(inflater, R.layout.fragment_carousel_item, container, false )

        context?.let { initView(binding, it) }

        return binding.root
    }

    private fun initView(binding: FragmentCarouselItemBinding, context: Context) {

        ayahAdapter = AdapterAyat(context)
        localDatabase = LocalDatabase.getDatabase(context)

        binding.recyclerviewList.adapter = ayahAdapter
        binding.recyclerviewList.setHasFixedSize(true)

        val bundle = arguments
        bundle.let {
            var carouselBundle = it?.getString("carouselItemBundle")
            var ayahItemBundle = it?.getString("ayahItemBundle")

            carouselItem = Gson().fromJson(carouselBundle, CarouselResponse.CarouselData.SurahData::class.java)

        }

        carouselItem.let {
            binding.textViewName.text = it.name
            binding.textViewEnglishName.text = it.englishName
            surahName = it.englishName.toString()
            binding.textViewTranslation.text = it.englishNameTranslation
            binding.textViewRevelationType.text = it.revelationType
        }

        fetchAyahData(context)

    }

    private fun fetchAyahData(context: Context) {
        localDatabase.getAyahDao().getAyahList().observe(viewLifecycleOwner, object :
            Observer<List<AyahsData>> {
            override fun onChanged(data: List<AyahsData>) {

                if(data.size != 0){
                    ayahList.clear()

                    for(item in data){
                        if(surahName.toLowerCase() == item.englishName?.toLowerCase()){
                            ayahList.add(item)
                        }
                    }

                    ayahAdapter.setAyahDataList(ayahList)
                    ayahAdapter.notifyDataSetChanged()

                    return
                }

            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): CarouselItemFragment {
            val fragment = CarouselItemFragment()
            return fragment
        }
    }
}