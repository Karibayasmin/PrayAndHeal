package com.kariba.prayheal.di.modules

import android.content.Context
import com.kariba.prayheal.localDatabase.LocalDatabase
import com.kariba.prayheal.preference.AppPreferenceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class CommonModule {

    @Provides
    fun getPreference(context: Context): AppPreferenceImpl{
        return  AppPreferenceImpl(context)
    }

    @Provides
    fun getLocalDatabase(context: Context) : LocalDatabase {

        return  LocalDatabase.getDatabase(context)
    }
}