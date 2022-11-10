package com.kariba.prayheal.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kariba.prayheal.models.CarouselResponse
import com.kariba.prayheal.network.ApiClient
import com.kariba.prayheal.network.ApiHandler
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.net.SocketTimeoutException

class MainViewModel : ViewModel() {

    var carouselMutableResponse = MutableLiveData<CarouselResponse>()

    fun getCarouselResponse(context: Context): LiveData<CarouselResponse>{
        carouselMutableResponse = MutableLiveData()
        loadCarouselResponse(context)
        return carouselMutableResponse
    }

    private fun loadCarouselResponse(context: Context) {
        var call = ApiClient.getInstance("")?.create(ApiHandler::class.java)?.getQuran()

        call?.enqueue(object : Callback<CarouselResponse> {
            override fun onResponse(
                call: Call<CarouselResponse>,
                response: Response<CarouselResponse>
            ) {
                if(response.isSuccessful && response.body() != null){
                    response.body()?.let { setCarouselResponseResponse(it) }
                }else{
                    if(response.errorBody() == null){
                        setCarouselResponseResponse(CarouselResponse(response.code(), response.message()))
                    }else{
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            setCarouselResponseResponse(CarouselResponse(response.code(), jObjError.getString("message") ?: response.message()))
                        }catch (e: Exception){
                            setCarouselResponseResponse(CarouselResponse(response.code(), e.message.toString()))
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CarouselResponse>, t: Throwable) {
                setCarouselResponseResponse(CarouselResponse(100, t.message))

                if(t is SocketTimeoutException){
                    setCarouselResponseResponse(CarouselResponse(408, t.message))

                }
            }

        })
    }

    fun setCarouselResponseResponse(data : CarouselResponse){
        carouselMutableResponse.value = data
    }
}