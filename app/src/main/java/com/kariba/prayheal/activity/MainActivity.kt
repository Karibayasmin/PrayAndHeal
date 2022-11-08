package com.kariba.prayheal.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.gson.Gson
import com.kariba.prayheal.R
import com.kariba.prayheal.adapter.AdapterCarouselView
import com.kariba.prayheal.databinding.ActivityMainBinding
import com.kariba.prayheal.interfaces.OnClickListener
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.network.ApiClient
import com.kariba.prayheal.network.ApiHandler
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var carouselAdapter: AdapterCarouselView

    var carouselList: ArrayList<CarouselResponse.CarouselData.SurahData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        initView()
    }

    private fun initView() {

        loadCarouselData()

        var snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewCarousel)

        val r: Resources = resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            80F,
            r.displayMetrics
        )
        val paddingWidth = (windowManager.defaultDisplay.width / 2) - px
        recyclerViewCarousel.setPadding(paddingWidth.toInt(), 0, paddingWidth.toInt(), 0)

        carouselAdapter = AdapterCarouselView(this@MainActivity, itemClick)
        recyclerViewCarousel.adapter = carouselAdapter
        recyclerViewCarousel.setHasFixedSize(true)

    }

    private fun loadCarouselData() {
        var call = ApiClient.getInstance("")?.create(ApiHandler::class.java)?.getQuran()

        call?.enqueue(object : Callback<CarouselResponse>{
            override fun onResponse(
                call: Call<CarouselResponse>,
                response: Response<CarouselResponse>
            ) {
                if(response.isSuccessful && response.body() != null){
                    response.body()?.let { setCarouselResponseResponse(it) }
                }else{
                    if(response.errorBody() == null){
                        setCarouselResponseResponse(CarouselResponse(response.code(), response.message()))
                    }else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            setCarouselResponseResponse(CarouselResponse(response.code(), jObjError.getString("message") ?: response.message()))
                        }catch (e: Exception){
                            setCarouselResponseResponse(CarouselResponse(response.code(), e.message.toString()))
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CarouselResponse>, t: Throwable) {
                setCarouselResponseResponse(CarouselResponse(100, t.message))
            }

        })
    }

    fun setCarouselResponseResponse(data : CarouselResponse){
        carouselList.clear()
        carouselList = data.carouselData?.surahList ?: ArrayList()

        carouselAdapter.setCarouselList(carouselList)
        carouselAdapter.notifyDataSetChanged()
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