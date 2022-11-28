package com.kariba.prayheal.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "edition")
class Edition(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = 0,

    @SerializedName("identifier")
    var identifier: String? = "",

    @SerializedName("language")
    var language: String? = "",

    @SerializedName("name")
    var name: String? = "",

    @SerializedName("englishName")
    var englishName: String? = "",

    @SerializedName("format")
    var format: String? = "",

    @SerializedName("type")
    var type: String? = "",

    ) : Serializable