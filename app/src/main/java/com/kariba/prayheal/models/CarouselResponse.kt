package com.kariba.prayheal.models

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kariba.prayheal.R
import java.io.Serializable

class CarouselResponse(
    @SerializedName("code")
    var responseCode: Int? = 0,

    @SerializedName("status")
    var userMessage: String? = "",

    @SerializedName("data")
    var carouselData: CarouselData? = CarouselData(),

    ) : Serializable {

    class CarouselData(
        @SerializedName("surahs")
        var surahList: ArrayList<SurahData>? = ArrayList(),

        @SerializedName("edition")
        var edition: Edition? = null

    ) : Serializable {
        class SurahData(

            @SerializedName("number")
            var number: Int? = 0,

            @SerializedName("name")
            var name: String? = "",

            @SerializedName("englishName")
            var englishName: String? = "",

            @SerializedName("englishNameTranslation")
            var englishNameTranslation: String? = "",

            @SerializedName("revelationType")
            var revelationType: String? = "",

            @SerializedName("ayahs")
            var ayahsList: ArrayList<AyahsData> = ArrayList()

        ) : Serializable
    }

}