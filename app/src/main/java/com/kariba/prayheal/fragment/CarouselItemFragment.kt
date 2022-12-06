package com.kariba.prayheal.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.kariba.prayheal.R
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.adapter.AdapterAyat
import com.kariba.prayheal.databinding.FragmentCarouselItemBinding
import com.kariba.prayheal.interfaces.OnCarouselClickListener
import com.kariba.prayheal.localDatabase.LocalDatabase
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.models.SurahData
import com.kariba.prayheal.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_carousel_item.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CarouselItemFragment : Fragment(), OnCarouselClickListener {

    var carouselItem : CarouselResponse.CarouselData.SurahData = CarouselResponse.CarouselData.SurahData()
    var ayahList: ArrayList<AyahsData> = ArrayList()

    @Inject
    lateinit var ayahAdapter : AdapterAyat

    @Inject
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

        val component = UserApplication.appComponent
        component.inject(this)

        context?.let { initView(binding, it) }

        return binding.root
    }

    private fun initView(binding: FragmentCarouselItemBinding, context: Context) {

        binding.recyclerviewList.adapter = ayahAdapter
        binding.recyclerviewList.setHasFixedSize(true)
        ayahAdapter.onFavoriteAyahClick = this

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

    override fun onClick(view: View, position: Int) {
        showSuccessAlert(false, ayahList[position])
    }

    private fun showSuccessAlert(isShowError: Boolean, ayahsData: AyahsData) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialog_custom)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleText = dialog.findViewById(R.id.titleText) as AppCompatTextView
        val messageText = dialog.findViewById(R.id.messageText) as AppCompatTextView
        val buttonCross = dialog.findViewById(R.id.crossButton) as AppCompatImageView
        val buttonOk = dialog.findViewById(R.id.okButton) as MaterialButton
        val topBackground = dialog.findViewById(R.id.topCardView) as MaterialCardView
        val topImageView = dialog.findViewById(R.id.topCardImage) as AppCompatImageView

        titleText.text = getString(R.string.app_name)

        if(ayahsData.isFavorite == false){
            messageText.text = "Do you want to make this Ayah favorite?"

        }else{
            messageText.text = "Do you want to undo this Ayah from favorite?"
        }

        titleText.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isShowError) R.color.colorAccent else R.color.black
            )
        )

        topBackground.setCardBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                if (isShowError) R.color.colorAccent else R.color.white_snow
            )
        )
        buttonOk.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                if (isShowError) R.color.colorAccent else R.color.red_apple
            )
        )
        topImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                if (isShowError) R.drawable.ic_favorite else R.drawable.ic_favorite
            )
        )

        buttonCross.setOnClickListener {

            dialog.dismiss()
        }

        buttonOk.setOnClickListener {

            if(ayahsData.isFavorite == false){
                GlobalScope.launch {
                    var updateAyah = AyahsData()
                    ayahsData.let {
                        updateAyah.id = it.id
                        updateAyah.isFavorite = true
                        updateAyah.text = it.text
                        updateAyah.englishName = it.englishName
                        updateAyah.page = it.page
                        updateAyah.number = it.number
                        updateAyah.numberInSurah = it.numberInSurah
                        updateAyah.ruku = it.ruku
                        updateAyah.manzil = it.manzil
                        updateAyah.juz = it.juz
                        updateAyah.hizbQuarter = it.hizbQuarter
                    }
                    localDatabase.getAyahDao().updateAyah(updateAyah)
                }

                AppUtils.showCustomToast(requireContext(), getString(R.string.successfully_added), false, getString(R.string.toast_type_success))

            }else{

                GlobalScope.launch {
                    var updateAyah = AyahsData()
                    ayahsData.let {
                        updateAyah.id = it.id
                        updateAyah.isFavorite = false
                        updateAyah.text = it.text
                        updateAyah.englishName = it.englishName
                        updateAyah.page = it.page
                        updateAyah.number = it.number
                        updateAyah.numberInSurah = it.numberInSurah
                        updateAyah.ruku = it.ruku
                        updateAyah.manzil = it.manzil
                        updateAyah.juz = it.juz
                        updateAyah.hizbQuarter = it.hizbQuarter
                    }
                    localDatabase.getAyahDao().updateAyah(updateAyah)
                }

                AppUtils.showCustomToast(requireContext(), getString(R.string.successfully_removed), false, getString(R.string.toast_type_success))
            }

            fetchAyahData(requireContext())

            dialog.dismiss()
        }

        dialog.show()
    }
}