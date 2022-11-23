package com.kariba.prayheal.di.component

import android.content.Context
import com.kariba.prayheal.UserApplication
import com.kariba.prayheal.activity.FragmentActivity
import com.kariba.prayheal.activity.InitialActivity
import com.kariba.prayheal.activity.MainActivity
import com.kariba.prayheal.di.modules.CommonModule
import com.kariba.prayheal.di.modules.MainActivityModule
import com.kariba.prayheal.di.modules.QuranFragmentModule
import com.kariba.prayheal.fragment.AlQuranFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CommonModule::class, MainActivityModule::class, QuranFragmentModule::class])
interface AppComponent {

    fun inject(userApplication: UserApplication)
    fun inject(initialActivity: InitialActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(fragmentActivity: FragmentActivity)
    fun inject(alQuranFragment: AlQuranFragment)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : AppComponent
    }
}