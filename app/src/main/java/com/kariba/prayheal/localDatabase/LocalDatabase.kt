package com.kariba.prayheal.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kariba.prayheal.localDatabase.dao.AyahDao
import com.kariba.prayheal.localDatabase.dao.SurahDao
import com.kariba.prayheal.models.AyahsData
import com.kariba.prayheal.models.Edition
import com.kariba.prayheal.models.SurahData

@Database(entities = [Edition::class, AyahsData::class, SurahData::class], version = 9, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun getAyahDao() : AyahDao
    abstract fun getSurahDao() : SurahDao

    companion object{
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE quran ADD COLUMN id INT NOT NULL PRIMARY KEY")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE quran")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE carousel")
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE surah")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE surah ADD COLUMN id INT NOT NULL PRIMARY KEY")
            }
        }

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE surah ADD COLUMN id INT NOT NULL PRIMARY KEY")
            }
        }

        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ayah ADD COLUMN englishName TEXT NOT NULL DEFAULT ''")
            }
        }

        val MIGRATION_8_9 = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE surah ADD COLUMN isFavorite INTEGER")
            }
        }

        @Volatile
        private var INSTANCE : LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, LocalDatabase::class.java, "quranDB")
                        .allowMainThreadQueries()
                        .enableMultiInstanceInvalidation()
                        .addMigrations(MIGRATION_1_2,
                            MIGRATION_2_3,
                            MIGRATION_3_4,
                            MIGRATION_4_5,
                            MIGRATION_5_6,
                            MIGRATION_6_7,
                            MIGRATION_7_8,
                            MIGRATION_8_9)
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

    fun clearAllData() {
        INSTANCE?.clearAllTables()
    }

}