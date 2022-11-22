package com.kariba.prayheal.preference

interface AppPreference {
    companion object{
        const val USER_NAME = "user_name"
        const val IS_LOGGED_IN = "is_logged_in"

    }

    fun setString (key: String, value: String)
    fun getString (key: String): String?
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean?

    fun clearAll()
}