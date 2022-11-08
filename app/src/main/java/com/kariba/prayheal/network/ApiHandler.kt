package com.kariba.prayheal.network

import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.utils.EndPoints
import retrofit2.Call
import retrofit2.http.GET

interface ApiHandler {
  @GET(EndPoints.API_GET_QURAN)
  fun getQuran() : Call<CarouselResponse>

}