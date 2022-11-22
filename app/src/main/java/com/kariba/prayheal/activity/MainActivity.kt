package com.kariba.prayheal.activity

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.gson.Gson
import com.kariba.prayheal.R
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.adapter.AdapterCarouselView
import com.kariba.prayheal.adapter.AdapterFavoriteAyat
import com.kariba.prayheal.databinding.ActivityMainBinding
import com.kariba.prayheal.interfaces.OnClickListener
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.preference.AppPreference
import com.kariba.prayheal.preference.AppPreferenceImpl
import com.kariba.prayheal.utils.AppUtils
import com.kariba.prayheal.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    private lateinit var carouselAdapter: AdapterCarouselView
    private lateinit var adapterFavoriteAyat : AdapterFavoriteAyat

    @Inject
    lateinit var appPreferenceImpl: AppPreferenceImpl

    lateinit var carouselViewModel : MainViewModel

    var carouselList: ArrayList<CarouselResponse.CarouselData.SurahData> = ArrayList()
    var ayatList: ArrayList<CarouselResponse.CarouselData.SurahData.AyahsData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = (application as UserApplication).appComponent
        component.inject(this)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        carouselViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.mainViewModel = carouselViewModel

        initView(binding)
    }

    private fun initView(binding: ActivityMainBinding) {

        binding.textViewUserName.text = appPreferenceImpl.getString(AppPreference.USER_NAME)

        loadCarouselData()

        var snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewCarousel)

        val r: Resources = resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            146F,
            r.displayMetrics
        )
        val paddingWidth = (windowManager.defaultDisplay.width / 2) - px
        recyclerViewCarousel.setPadding(paddingWidth.toInt(), 0, paddingWidth.toInt(), 0)

        carouselAdapter = AdapterCarouselView(this@MainActivity, itemClick)
        recyclerViewCarousel.adapter = carouselAdapter
        recyclerViewCarousel.setHasFixedSize(true)

        adapterFavoriteAyat = AdapterFavoriteAyat(this@MainActivity, ayatClick)
        recyclerViewGrid.adapter = adapterFavoriteAyat
        recyclerViewGrid.layoutManager = GridLayoutManager(this, 2)
        recyclerViewGrid.setHasFixedSize(true)

    }

    private fun loadCarouselData() {
        if(!AppUtils.hasNetworkConnection(this)){
            AppUtils.showToast(this, "Please Check your network connection", false)
            return
        }

        progressDialog.show()

        carouselViewModel.getCarouselResponse(this).observe(this, object : Observer<CarouselResponse>{
            override fun onChanged(data: CarouselResponse?) {
                progressDialog.dismiss()
                if(data?.responseCode == 200){
                    carouselList.clear()
                    carouselList = data?.carouselData?.surahList ?: ArrayList()

                    carouselAdapter.setCarouselList(carouselList)
                    carouselAdapter.notifyDataSetChanged()

                    ayatList.clear()
                    ayatList = data.carouselData?.surahList?.get(1)?.ayahsList ?: ArrayList()

                    adapterFavoriteAyat.setAyatData(ayatList)
                    adapterFavoriteAyat.notifyDataSetChanged()

                }else{
                    AppUtils.showToast(this@MainActivity, "Something went wrong, please try again", false)
                }


            }

        })
    }

    private var itemClick = object : OnClickListener {
        override fun onClick(view: View, position: Int) {
            var intent = Intent(this@MainActivity, FragmentActivity::class.java)
            var bundle = Bundle()
            bundle.putString("fragment", "carouselFragment")
            bundle.putString("carouselItem", Gson().toJson(carouselList[position]))
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }

    private var ayatClick = object : OnClickListener{
        override fun onClick(view: View, position: Int) {
            showCustomAlert(ayatList[position].text ?: "")
        }

    }

    private fun showCustomAlert(message: String) {
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textViewMessage = dialog.findViewById(R.id.textView_message) as AppCompatTextView
        val buttonClose = dialog.findViewById(R.id.imageView_close) as AppCompatImageView

        textViewMessage.text = message

        buttonClose.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }
}