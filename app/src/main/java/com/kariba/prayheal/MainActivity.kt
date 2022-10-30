package com.kariba.prayheal

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.kariba.prayheal.adapter.AdapterCarouselView
import com.kariba.prayheal.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var carouselAdapter : AdapterCarouselView

    var carouselList : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        initView()
    }

    private fun initView() {
        carouselList.add("Demo 1st Name")
        carouselList.add("Demo 2nd Name")
        carouselList.add("Demo 3rd Name")
        carouselList.add("Demo 4th Name")
        carouselList.add("Demo 5th Name")
        carouselList.add("Demo 6th Name")

        var snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewCarousel)

        val r: Resources = resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            80F,
            r.displayMetrics
        )
        val paddingWidth = (windowManager.defaultDisplay.width / 2) - px
        recyclerViewCarousel.setPadding(paddingWidth.toInt(), 0, paddingWidth.toInt(), 0)

        carouselAdapter = AdapterCarouselView(this@MainActivity)
        recyclerViewCarousel.adapter = carouselAdapter
        recyclerViewCarousel.setHasFixedSize(true)

        carouselAdapter.setCarouselList(carouselList)


    }
}