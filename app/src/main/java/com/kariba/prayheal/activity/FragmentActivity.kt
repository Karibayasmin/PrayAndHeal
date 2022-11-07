package com.kariba.prayheal.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.kariba.prayheal.R
import com.kariba.prayheal.fragment.CarouselItemFragment
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val bundle = Bundle()


        intent.extras?.getString("fragment")?.let {
            when(it){
                "carouselFragment" -> {

                    var carouselItemBundle = intent.extras?.getString("carouselItem")

                    Log.e("inside this", "1 $carouselItemBundle")

                    val carouselFragment : CarouselItemFragment by lazy { CarouselItemFragment.newInstance() }
                    commitFragmentTransactionAdd(carouselFragment, carouselItemBundle)
                }
            }
        }

        toolbar.setOnClickListener {
            onBackPressed()
        }


    }


    fun commitFragmentTransactionAdd(fragment: Fragment, carouselItemBundle: String?) {
        try {
            var bundle = Bundle()
            bundle.putString("carouselItemBundle", carouselItemBundle.toString())

            fragment.arguments  = bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerFragment, fragment)
                .commit()

        } catch (exception: Exception) {
            Log.e("exception", "$exception")
        }
    }

}