package com.kariba.prayheal.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "ayah")
class AyahsData(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @SerializedName("number")
    var number: Int? = 0,

    @SerializedName("text")
    var text: String? = "",

    @SerializedName("numberInSurah")
    var numberInSurah: Int? = 0,

    @SerializedName("juz")
    var juz: Int? = 0,

    @SerializedName("manzil")
    var manzil: Int? = 0,

    @SerializedName("page")
    var page: Int? = 0,

    @SerializedName("ruku")
    var ruku: Int? = 0,

    @SerializedName("hizbQuarter")
    var hizbQuarter: Int? = 0,

    @SerializedName("englishName")
    var englishName: String? = "",

    @SerializedName("isFavorite")
    var isFavorite: Boolean? = false

) : Serializable