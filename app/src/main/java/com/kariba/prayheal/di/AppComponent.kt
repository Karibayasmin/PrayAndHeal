package com.kariba.prayheal.di

import android.content.Context
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.activity.InitialActivity
import com.kariba.prayheal.activity.MainActivity
import com.kariba.prayheal.di.modules.CommonModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CommonModule::class])
interface AppComponent {

    fun inject(userApplication: UserApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(initialActivity: InitialActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }
}