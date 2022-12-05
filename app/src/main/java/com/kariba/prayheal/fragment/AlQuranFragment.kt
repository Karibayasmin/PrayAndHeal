package com.kariba.prayheal.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.kariba.prayheal.R
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.activity.FragmentActivity
import com.kariba.prayheal.adapter.AdapterSurah
import com.kariba.prayheal.databinding.FragmentAlQuranBinding
import com.kariba.prayheal.interfaces.OnItemClickListener
import com.kariba.prayheal.localDatabase.LocalDatabase
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.models.SurahData
import com.kariba.prayheal.utils.AppConstants
import com.kariba.prayheal.utils.AppUtils
import com.kariba.prayheal.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class AlQuranFragment : BaseFragment(), TextWatcher, OnItemClickListener {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var adapterSurah: AdapterSurah

    lateinit var localDatabase: LocalDatabase

    var surahList: ArrayList<SurahData> = ArrayList()
    var surahTemporaryList: ArrayList<SurahData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAlQuranBinding>(
            inflater,
            R.layout.fragment_al_quran,
            container,
            false
        )

        val component = UserApplication.appComponent
        component.inject(this)

        context?.let { initView(binding, it) }

        return binding.root
    }

    private fun initView(binding: FragmentAlQuranBinding, context: Context) {

        localDatabase = LocalDatabase.getDatabase(context)

        Log.e("AlQuranFragment", "Enter here 2")

        fetchSurahData(context)

        binding.recyclerViewSurah.adapter = adapterSurah
        binding.recyclerViewSurah.setHasFixedSize(true)

        binding.editTextSearch.addTextChangedListener(this)
        adapterSurah.onItemClickListener = this


    }

    private fun fetchSurahData(context: Context) {
        localDatabase.getSurahDao().getSurahList().observe(viewLifecycleOwner, object : Observer<List<SurahData>>{
            override fun onChanged(data: List<SurahData>) {

                if(data.size != 0){
                    surahList.clear()
                    surahList = data as ArrayList<SurahData>
                    updateList()

                    return
                }else {
                    loadSurahData(context)
                }
            }

        })
    }

    private fun loadSurahData(context: Context) {
        if (!AppUtils.hasNetworkConnection(context)) {
            AppUtils.showToast(context, getString(R.string.no_internet), false)
            return
        }

        progressDialog.show()

        mainViewModel.getCarouselResponse(context)
            .observe(viewLifecycleOwner, object : Observer<CarouselResponse> {
                override fun onChanged(data: CarouselResponse) {

                    progressDialog.dismiss()

                    localDatabase.clearAllData()

                    GlobalScope.launch {
                        for((index, item) in data.carouselData?.surahList?.withIndex() ?: ArrayList()){

                            var surahData = SurahData()
                            item.let {
                                surahData.englishName = it.englishName
                                surahData.name = it.name
                                surahData.number = it.number
                                surahData.englishNameTranslation = it.englishNameTranslation
                                surahData.revelationType = it.revelationType
                            }

                            localDatabase.getSurahDao().insertSurah(surahData)


                            for((index, value) in item.ayahsList.withIndex()){
                                var ayahsData = AyahsData()

                                value.let {
                                    ayahsData.hizbQuarter = it.hizbQuarter
                                    ayahsData.number = it.number
                                    ayahsData.juz = it.juz
                                    ayahsData.numberInSurah = it.numberInSurah
                                    ayahsData.manzil = it.manzil
                                    ayahsData.ruku = it.ruku
                                    ayahsData.page = it.page
                                    ayahsData.text = it.text
                                    ayahsData.englishName = item.englishName
                                }

                                localDatabase.getAyahDao().insertAyah(ayahsData)
                            }


                            if(index == 144){
                                fetchSurahData(context)
                            }
                        }
                    }

                }

            })
    }

    companion object {
        fun newInstance(): AlQuranFragment {
            val fragment = AlQuranFragment()
            return fragment
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        updateList(s.toString())
    }

    private fun updateList(query: String? = "") {

        surahTemporaryList.clear()


        surahList?.forEach { surahData ->
            if ((surahData.englishName ?: "").toLowerCase()
                    ?.contains(query?.toLowerCase() ?: "")
            ) {
                surahTemporaryList.add(surahData)

                adapterSurah.setSurahData(surahTemporaryList)
                adapterSurah.notifyDataSetChanged()
            }

        }
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onClick(view: View, position: Int) {
        var intent = Intent(requireContext(), FragmentActivity::class.java)
        var bundle = Bundle()
        bundle.putString("fragment", AppConstants.CAROUSEL_FRAGMENT)
        bundle.putString("carouselItem", Gson().toJson(surahTemporaryList[position]))
        intent.putExtras(bundle)
        startActivity(intent)
    }
}