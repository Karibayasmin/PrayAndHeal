package com.kariba.prayheal.localDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kariba.prayheal.models.AyahsData

@Dao
interface AyahDao {

    @Insert
    fun insertAyah(ayahsData: AyahsData)

    @Update
    fun updateAyah(ayahsData: AyahsData)

    @Delete
    fun deleteAyah(ayahsData: AyahsData)

    @Query("SELECT * FROM ayah")
    fun getAyahList() : LiveData<List<AyahsData>>

}