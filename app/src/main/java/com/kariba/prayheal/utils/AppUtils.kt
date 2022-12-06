package com.kariba.prayheal.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.kariba.prayheal.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

object AppUtils {

    fun showToast(context: Context, message : String, isLong : Boolean){
        Toast.makeText(context, message, if(isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
    }

    fun hasNetworkConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun showCustomToast(context: Context, message: String, isLong: Boolean, toastType : String){
        MotionToast.darkColorToast(
            context as Activity,

            when(toastType){
                context.getString(R.string.toast_type_success) -> "Success"
                context.getString(R.string.toast_type_error) -> "Error"
                context.getString(R.string.toast_type_warning) -> "Warning"
                else -> ""
            },
            message,
            when(toastType){
                context.getString(R.string.toast_type_success) -> MotionToastStyle.SUCCESS
                context.getString(R.string.toast_type_error) -> MotionToastStyle.ERROR
                context.getString(R.string.toast_type_warning) -> MotionToastStyle.WARNING
                else -> MotionToastStyle.SUCCESS
            },
            MotionToast.GRAVITY_BOTTOM,
            if (isLong) MotionToast.LONG_DURATION else MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(context, www.sanju.motiontoast.R.font.helvetica_regular))
    }
}