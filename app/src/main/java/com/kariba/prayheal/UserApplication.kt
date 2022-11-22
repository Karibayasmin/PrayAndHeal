package com.kariba.prayheal

import android.app.Application
import com.kariba.prayheal.di.AppComponent
import com.kariba.prayheal.di.DaggerAppComponent

class UserApplication : Application() {

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
        appComponent.inject(this)

    }
}