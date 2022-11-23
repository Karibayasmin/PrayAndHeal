package com.kariba.prayheal.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kariba.prayheal.R
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.databinding.ActivityInitialBinding
import com.kariba.prayheal.preference.AppPreference
import com.kariba.prayheal.preference.AppPreferenceImpl
import com.kariba.prayheal.utils.AppUtils
import kotlinx.android.synthetic.main.activity_initial.*
import javax.inject.Inject

class InitialActivity : AppCompatActivity() {

    @Inject
    lateinit var appPreferenceImpl: AppPreferenceImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityInitialBinding>(this, R.layout.activity_initial)


        UserApplication.appComponent.inject(this)

        binding.lifecycleOwner = this

        initView(binding)

    }

    private fun initView(binding: ActivityInitialBinding) {
        if(appPreferenceImpl.getBoolean(AppPreference.IS_LOGGED_IN) == true){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.textViewSubmit.setOnClickListener {
            if(isValid()){
                appPreferenceImpl.setString(AppPreference.USER_NAME, binding.editTextName.text.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun isValid() : Boolean{
        if(editTextName.text?.isEmpty() == true){
            AppUtils.showToast(this, getString(R.string.please_enter_name), false)
            return false
        }

        return true
    }
}