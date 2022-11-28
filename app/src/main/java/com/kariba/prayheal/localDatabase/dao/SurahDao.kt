package com.kariba.prayheal.localDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kariba.prayheal.models.Surah

@Dao
interface SurahDao {

    @Insert
    fun insertSurah (surah: Surah)

    @Update
    fun updateSurah (surah: Surah)

    @Delete
    fun deleteSurah (surah: Surah)

    @Query("SELECT * FROM surah")
    fun getSurahList () : LiveData<List<Surah>>
}