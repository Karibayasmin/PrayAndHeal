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
import com.kariba.prayheal.databinding.FragmentCarouselItemBinding
import com.kariba.prayheal.models.CarouselResponse

class CarouselItemFragment : Fragment() {

    var carouselItem : CarouselResponse.CarouselData.SurahData = CarouselResponse.CarouselData.SurahData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentCarouselItemBinding>(inflater, R.layout.fragment_carousel_item, container, false )

        initView(binding, context)

        return binding.root
    }

    private fun initView(binding: FragmentCarouselItemBinding, context: Context?) {
        val bundle = arguments

        bundle.let {
            var carouselBundle = it?.getString("carouselItemBundle")

            Log.e("inside this", "2 $carouselBundle")

            carouselItem = Gson().fromJson(carouselBundle, CarouselResponse.CarouselData.SurahData::class.java)
        }

        carouselItem.let {
            binding.textViewNameTypeOne.text = carouselItem.name
            binding.textViewNameTypeTwo.text = carouselItem.englishName
            binding.textViewNameTypeThree.text = carouselItem.englishNameTranslation
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