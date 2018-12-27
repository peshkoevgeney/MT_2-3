package com.example.myapplication123

import android.content.Context
import androidx.room.*

@Database(entities = arrayOf(Category::class), version = 1)
@TypeConverters(Converters::class)
abstract class CatImageDatabase : RoomDatabase() {
abstract fun CatDao(): CatDao

        companion object {

@Volatile private var INSTANCE: CatImageDatabase? = null

        fun getInstance(context: Context): CatImageDatabase =
        INSTANCE ?: synchronized(this) {
        INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

private fun buildDatabase(context: Context) =
        Room.databaseBuilder(context.applicationContext,
        CatImageDatabase::class.java,"catsimagedatabase" )
        .build()
        }
        }