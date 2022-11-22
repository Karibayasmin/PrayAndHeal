package com.kariba.prayheal.di.modules

import android.content.Context
import com.kariba.prayheal.adapter.AdapterCarouselView
import com.kariba.prayheal.adapter.AdapterFavoriteAyat
import com.kariba.prayheal.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun getAdapterCarouselView(context: Context) : AdapterCarouselView{

        return AdapterCarouselView(context)
    }

    @Provides
    fun getAdapterFavoriteAyat(context: Context) : AdapterFavoriteAyat{

        return AdapterFavoriteAyat(context)
    }

    @Provides
    fun getMainViewModel() : MainViewModel{

        return  MainViewModel()
    }

}