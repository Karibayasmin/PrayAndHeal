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
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.utils.AppConstants
import com.kariba.prayheal.utils.AppUtils
import com.kariba.prayheal.viewmodels.MainViewModel
import javax.inject.Inject


class AlQuranFragment : BaseFragment(), TextWatcher, OnItemClickListener {

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var adapterSurah: AdapterSurah

    var surahList: ArrayList<CarouselResponse.CarouselData.SurahData> = ArrayList()
    var surahTemporaryList: ArrayList<CarouselResponse.CarouselData.SurahData> = ArrayList()

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

        Log.e("AlQuranFragment", "Enter here 2")

        loadSurahData(context)

        binding.recyclerViewSurah.adapter = adapterSurah
        binding.recyclerViewSurah.setHasFixedSize(true)

        binding.editTextSearch.addTextChangedListener(this)
        adapterSurah.onItemClickListener = this


    }

    private fun loadSurahData(context: Context) {
        if (!AppUtils.hasNetworkConnection(context)) {
            AppUtils.showToast(context, getString(R.string.no_internet), false)
            return
        }

        progressDialog.show()

        mainViewModel.getCarouselResponse(context)
            .observe(viewLifecycleOwner, object : Observer<CarouselResponse> {
                override fun onChanged(data: CarouselResponse?) {

                    progressDialog.dismiss()

                    surahList.clear()
                    surahList = data?.carouselData?.surahList ?: ArrayList()

                    updateList()

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