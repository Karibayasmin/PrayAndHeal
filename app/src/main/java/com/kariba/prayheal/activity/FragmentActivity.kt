package com.kariba.prayheal.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.kariba.prayheal.R
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.fragment.AlQuranFragment
import com.kariba.prayheal.fragment.CarouselItemFragment
import com.kariba.prayheal.fragment.RulesFragment
import com.kariba.prayheal.fragment.TasbihFragment
import com.kariba.prayheal.utils.AppConstants
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        intent.extras?.getString("fragment")?.let {
            when(it){
                AppConstants.CAROUSEL_FRAGMENT -> {

                    var carouselItemBundle = intent.extras?.getString("carouselItem")
                    var ayahItemBundle = intent.extras?.getString("ayahList")

                    val carouselFragment : CarouselItemFragment by lazy { CarouselItemFragment.newInstance() }
                    commitFragmentTransactionAdd(carouselFragment, AppConstants.CAROUSEL_FRAGMENT, carouselItemBundle, ayahItemBundle)
                }

                AppConstants.AL_QURAN_FRAGMENT -> {

                    val alQuranFragment : AlQuranFragment by lazy { AlQuranFragment.newInstance() }
                    commitFragmentTransactionAdd(alQuranFragment, AppConstants.AL_QURAN_FRAGMENT)
                }

                AppConstants.TASBIH_FRAGMENT -> {

                    val tasbihFragment : TasbihFragment by lazy { TasbihFragment.newInstance() }
                    commitFragmentTransactionAdd(tasbihFragment, AppConstants.TASBIH_FRAGMENT)
                }

                AppConstants.RULES_FRAGMENT -> {

                    val rulesFragment : RulesFragment by lazy { RulesFragment.newInstance() }
                    commitFragmentTransactionAdd(rulesFragment, AppConstants.RULES_FRAGMENT)
                }

            }
        }

        toolbar.setOnClickListener {
            onBackPressed()
        }


    }


    fun commitFragmentTransactionAdd(fragment: Fragment, tag : String, carouselItemBundle: String? = "", ayahItemBundle: String? = "") {
        try {
            var bundle = Bundle()
            bundle.putString("carouselItemBundle", carouselItemBundle.toString())
            bundle.putString("ayahItemBundle", carouselItemBundle.toString())

            fragment.arguments  = bundle

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.containerFragment, fragment, tag)
                .commit()

        } catch (exception: Exception) {
            Log.e("exception", "$exception")
        }
    }

}