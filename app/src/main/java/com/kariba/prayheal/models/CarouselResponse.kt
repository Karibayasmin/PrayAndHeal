package com.kariba.prayheal.models

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.google.gson.annotations.SerializedName
import com.kariba.prayheal.R
import java.io.Serializable


class CarouselResponse (
    @SerializedName("code")
    var responseCode : Int? = 0,

    @SerializedName("status")
    var userMessage : String? = "",

    @SerializedName("data")
    var carouselData : CarouselData? = CarouselData(),

    ):Serializable{
        class CarouselData(
            @SerializedName("surahs")
            var surahList : ArrayList<SurahData>? = ArrayList(),

            @SerializedName("edition")
            var edition : Edition? = null

        ): Serializable{
            class SurahData(
                @SerializedName("number")
                var number : Int? = 0,

                @SerializedName("name")
                var name : String? = "",

                @SerializedName("englishName")
                var englishName : String? = "",

                @SerializedName("englishNameTranslation")
                var englishNameTranslation : String? = "",

                @SerializedName("revelationType")
                var revelationType : String? = "",

                @SerializedName("ayahs")
                var ayahsList : ArrayList<AyahsData> = ArrayList()

            ):Serializable{
                class AyahsData(
                    @SerializedName("number")
                    var number : Int? = 0,

                    @SerializedName("text")
                    var text : String? = "",

                    @SerializedName("numberInSurah")
                    var numberInSurah : Int? = 0,

                    @SerializedName("juz")
                    var juz : Int? = 0,

                    @SerializedName("manzil")
                    var manzil : Int? = 0,

                    @SerializedName("page")
                    var page : Int? = 0,

                    @SerializedName("ruku")
                    var ruku : Int? = 0,

                    @SerializedName("hizbQuarter")
                    var hizbQuarter : Int? = 0,
                ):Serializable
            }

            class Edition(
                @SerializedName("identifier")
                var identifier : String? = "",

                @SerializedName("language")
                var language : String? = "",

                @SerializedName("name")
                var name : String? = "",

                @SerializedName("englishName")
                var englishName : String? = "",

                @SerializedName("format")
                var format : String? = "",

                @SerializedName("type")
                var type : String? = "",

                ):Serializable
        }

    }