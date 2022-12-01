package com.kariba.prayheal.localDatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kariba.prayheal.models.SurahData

@Dao
interface SurahDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSurah (surahData: SurahData)

    @Update
    suspend fun updateSurah (surahData: SurahData)

    @Delete
    suspend fun deleteSurah (surahData: SurahData)

    @Query("SELECT * FROM surah")
    fun getSurahList () : LiveData<List<SurahData>>
}