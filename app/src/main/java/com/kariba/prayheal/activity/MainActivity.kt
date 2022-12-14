package com.kariba.prayheal.activity

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.gson.Gson
import com.kariba.prayheal.R
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.adapter.AdapterCarouselView
import com.kariba.prayheal.adapter.AdapterFavoriteAyat
import com.kariba.prayheal.databinding.ActivityMainBinding
import com.kariba.prayheal.interfaces.OnCarouselClickListener
import com.kariba.prayheal.interfaces.onAyatClickListener
import com.kariba.prayheal.localDatabase.LocalDatabase
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.models.SurahData
import com.kariba.prayheal.preference.AppPreference
import com.kariba.prayheal.preference.AppPreferenceImpl
import com.kariba.prayheal.utils.AppConstants
import com.kariba.prayheal.utils.AppUtils
import com.kariba.prayheal.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), OnCarouselClickListener, onAyatClickListener{

    @Inject
    lateinit var carouselAdapter: AdapterCarouselView

    @Inject
    lateinit var adapterFavoriteAyat : AdapterFavoriteAyat

    @Inject
    lateinit var appPreferenceImpl: AppPreferenceImpl

    @Inject
    lateinit var carouselViewModel : MainViewModel

    private var carouselList: ArrayList<SurahData> = ArrayList()
    private var ayatList: ArrayList<AyahsData> = ArrayList()

    @Inject
    lateinit var localDatabase : LocalDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UserApplication.appComponent.inject(this)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.mainViewModel = carouselViewModel

        initView(binding)
    }

    private fun initView(binding: ActivityMainBinding) {

        binding.textViewUserName.text = "${getString(R.string.dear)} ${appPreferenceImpl.getString(AppPreference.USER_NAME)}"
        appPreferenceImpl.setBoolean(AppPreference.IS_LOGGED_IN, true)

        loadFavoriteSurah(binding)
        loadFavoriteAyah(binding)

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

        carouselAdapter.itemClick = this
        recyclerViewCarousel.adapter = carouselAdapter
        recyclerViewCarousel.setHasFixedSize(true)

        adapterFavoriteAyat.ayatClick = this
        recyclerViewGrid.adapter = adapterFavoriteAyat
        recyclerViewGrid.layoutManager = GridLayoutManager(this, 2)
        recyclerViewGrid.setHasFixedSize(true)

        binding.layoutQuran.setOnClickListener {
            switchToAlQuran()
        }

        binding.layoutTasbih.setOnClickListener {
            switchToTasbih()
        }

        binding.layoutRules.setOnClickListener {
            switchToRules()
        }

    }

    private fun loadFavoriteAyah(binding: ActivityMainBinding) {
        localDatabase.getAyahDao().getAyahList().observe(this, object : Observer<List<AyahsData>>{
            override fun onChanged(data: List<AyahsData>?) {

                if(data?.size != 0){

                    binding.layoutAyahNoData.root.visibility = View.GONE
                    binding.recyclerViewGrid.visibility = View.VISIBLE

                    ayatList.clear()

                    for(item in data ?: ArrayList()){
                        if(item.isFavorite == true){
                            ayatList.add(item)
                        }
                    }

                    adapterFavoriteAyat.setAyatData(ayatList)
                    adapterFavoriteAyat.notifyDataSetChanged()

                    if(ayatList.size == 0){
                        binding.layoutAyahNoData.root.visibility = View.VISIBLE
                        binding.recyclerViewGrid.visibility = View.GONE
                    }


                }else {

                    binding.layoutAyahNoData.root.visibility = View.VISIBLE
                    binding.recyclerViewGrid.visibility = View.GONE

                }

            }

        })
    }

    private fun loadFavoriteSurah(binding: ActivityMainBinding) {
        localDatabase.getSurahDao().getSurahList().observe(this, object : Observer<List<SurahData>>{
            override fun onChanged(data: List<SurahData>?) {

                if(data?.size != 0){

                    binding.layoutSurahNoData.root.visibility = View.GONE
                    binding.recyclerViewCarousel.visibility = View.VISIBLE

                    carouselList.clear()

                    for(item in data ?: ArrayList()){
                        if(item.isFavorite == true){
                            carouselList.add(item)
                        }
                    }

                    carouselAdapter.setCarouselList(carouselList)
                    carouselAdapter.notifyDataSetChanged()

                    if(carouselList.size == 0){
                        binding.layoutSurahNoData.root.visibility = View.VISIBLE
                        binding.recyclerViewCarousel.visibility = View.GONE
                    }


                }else {

                    binding.layoutSurahNoData.root.visibility = View.VISIBLE
                    binding.recyclerViewCarousel.visibility = View.GONE

                }

            }

        })
    }

    override fun onClick(view: View, position: Int) {
        var intent = Intent(this@MainActivity, FragmentActivity::class.java)
        var bundle = Bundle()
        bundle.putString("fragment", AppConstants.CAROUSEL_FRAGMENT)
        bundle.putString("carouselItem", Gson().toJson(carouselList[position]))
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun switchToAlQuran(){
        var intent = Intent(this@MainActivity, FragmentActivity::class.java)
        var bundle = Bundle()
        bundle.putString("fragment", AppConstants.AL_QURAN_FRAGMENT)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun switchToTasbih(){
        var intent = Intent(this@MainActivity, FragmentActivity::class.java)
        var bundle = Bundle()
        bundle.putString("fragment", AppConstants.TASBIH_FRAGMENT)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun switchToRules(){
        var intent = Intent(this@MainActivity, FragmentActivity::class.java)
        var bundle = Bundle()
        bundle.putString("fragment", AppConstants.RULES_FRAGMENT)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun ayatOnClick(view: View, position: Int) {
        showCustomAlert(ayatList[position].text ?: "")
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