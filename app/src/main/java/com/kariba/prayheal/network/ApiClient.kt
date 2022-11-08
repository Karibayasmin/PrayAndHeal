package com.kariba.prayheal.network

import com.kariba.prayheal.BuildConfig
import com.kariba.prayheal.utils.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

object ApiClient {

    private var retrofit : Retrofit? = null
    private var okHttpClient : OkHttpClient? = null

    fun getInstance(token: String): Retrofit?{

        okHttpClient = null
        initOkHttp(token)

        retrofit = null
        retrofit = Retrofit
            .Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }

    private fun initOkHttp(token : String) {
        val REQUEST_TIMEOUT = 30
        val httpClient: OkHttpClient.Builder = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor(object : Interceptor {
            @NotNull
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Version-Code", BuildConfig.VERSION_NAME)
                    .addHeader("Authorization", "Bearer $token")

                val request: Request = requestBuilder.build()
                return chain.proceed(request)
            }

        })

        okHttpClient = httpClient.build()
    }
}