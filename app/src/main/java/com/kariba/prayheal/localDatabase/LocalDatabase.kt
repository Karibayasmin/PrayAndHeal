package com.kariba.prayheal.localDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kariba.prayheal.localDatabase.dao.AyahDao
import com.kariba.prayheal.localDatabase.dao.SurahDao
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.Edition
import com.kariba.prayheal.models.Surah

@Database(entities = [Edition::class, AyahsData::class, Surah::class], version = 6, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun getAyahDao() : AyahDao
    abstract fun getSurahDao() : SurahDao

}