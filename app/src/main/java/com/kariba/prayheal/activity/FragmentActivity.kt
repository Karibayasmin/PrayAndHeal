package com.kariba.prayheal.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.kariba.prayheal.R
import com.kariba.prayheal.fragment.AlQuranFragment
import com.kariba.prayheal.fragment.CarouselItemFragment
import com.kariba.prayheal.utils.AppConstants
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        val bundle = Bundle()


        intent.extras?.getString("fragment")?.let {
            when(it){
                AppConstants.CAROUSEL_FRAGMENT -> {

                    var carouselItemBundle = intent.extras?.getString("carouselItem")


                    val carouselFragment : CarouselItemFragment by lazy { CarouselItemFragment.newInstance() }
                    commitFragmentTransactionAdd(carouselFragment, carouselItemBundle)
                }

                AppConstants.AL_QURAN_FRAGMENT -> {

                    val alQuranFragment : AlQuranFragment by lazy { AlQuranFragment.newInstance() }
                    commitFragmentTransactionAdd(alQuranFragment, "")
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