package com.kariba.prayheal.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.kariba.prayheal.R
import com.kariba.prayheal.adapter.AdapterAyat
import com.kariba.prayheal.databinding.FragmentCarouselItemBinding
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.CarouselResponse
import kotlinx.android.synthetic.main.fragment_carousel_item.*

class CarouselItemFragment : Fragment() {

    var carouselItem : CarouselResponse.CarouselData.SurahData = CarouselResponse.CarouselData.SurahData()
    var ayahItem : AyahsData = AyahsData()

    lateinit var ayahAdapter : AdapterAyat

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

        binding.recyclerviewList.adapter = ayahAdapter
        binding.recyclerviewList.setHasFixedSize(true)

        val bundle = arguments
        bundle.let {
            var carouselBundle = it?.getString("carouselItemBundle")
            var ayahItemBundle = it?.getString("ayahItemBundle")


            carouselItem = Gson().fromJson(carouselBundle, CarouselResponse.CarouselData.SurahData::class.java)


            ayahAdapter.setAyahDataList(carouselItem.ayahsList)
            ayahAdapter.notifyDataSetChanged()
        }

        carouselItem.let {
            binding.textViewName.text = carouselItem.name
            binding.textViewEnglishName.text = carouselItem.englishName
            binding.textViewTranslation.text = carouselItem.englishNameTranslation
            binding.textViewRevelationType.text = carouselItem.revelationType
        }
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