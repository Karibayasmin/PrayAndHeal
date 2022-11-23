package com.kariba.prayheal.di.modules

import android.content.Context
import com.kariba.prayheal.adapter.AdapterSurah
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class QuranFragmentModule {

    @Provides
    fun getAdapterSurah(context: Context): AdapterSurah{

        return AdapterSurah(context)
    }
}