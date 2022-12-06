package com.kariba.prayheal.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "surah")
class SurahData (
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

    @SerializedName("isFavorite")
    var isFavorite: Boolean? = false

) : Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}