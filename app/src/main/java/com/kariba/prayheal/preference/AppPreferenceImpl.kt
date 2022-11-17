package com.kariba.prayheal.preference

import android.content.Context
import com.kariba.prayheal.utils.AppConstants

class AppPreferenceImpl(context: Context): AppPreference {

    private val sharedPreferences = context.getSharedPreferences(AppConstants.SHARED_PREF_TABLE_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun setString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    override fun clearAll() {
        editor.clear()
        editor.apply()
    }
}