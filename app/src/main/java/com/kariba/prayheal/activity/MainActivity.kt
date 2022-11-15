package com.kariba.prayheal.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.gson.Gson
import com.kariba.prayheal.R
import com.kariba.prayheal.adapter.AdapterCarouselView
import com.kariba.prayheal.adapter.AdapterFavoriteAyat
import com.kariba.prayheal.databinding.ActivityMainBinding
import com.kariba.prayheal.interfaces.OnClickListener
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.network.ApiClient
import com.kariba.prayheal.network.ApiHandler
import com.kariba.prayheal.utils.AppUtils
import com.kariba.prayheal.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var carouselAdapter: AdapterCarouselView
    private lateinit var adapterFavoriteAyat : AdapterFavoriteAyat

    lateinit var carouselViewModel : MainViewModel

    var carouselList: ArrayList<CarouselResponse.CarouselData.SurahData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        carouselViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.mainViewModel = carouselViewModel

        initView()
    }

    private fun initView() {

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

        adapterFavoriteAyat = AdapterFavoriteAyat(this@MainActivity)
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

                    adapterFavoriteAyat.setAyatData(data.carouselData?.surahList?.get(1)?.ayahsList ?: ArrayList())
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
}