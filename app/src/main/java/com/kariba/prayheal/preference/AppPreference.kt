package com.kariba.prayheal.preference

interface AppPreference {
    companion object{
        const val USER_NAME = "user_name"

    }

    fun setString (key: String, value: String)
    fun getString (key: String): String?

    fun clearAll()
}