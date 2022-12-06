package com.kariba.prayheal.di.modules

import android.content.Context
import com.kariba.prayheal.adapter.AdapterAyat
import dagger.Module
import dagger.Provides

@Module
class CarouselItemFragmentModule {

    @Provides
    fun getAdapterAyah(context: Context): AdapterAyat {

        return AdapterAyat(context)
    }
}