package com.demo.newsapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.demo.newsapplication.data.database.daos.ChatDao
import com.demo.newsapplication.data.database.entities.ArticleTypeConvertor
import com.demo.newsapplication.data.database.entities.SourceTypeConverters
import com.demo.newsapplication.models.Article
import com.demo.newsapplication.models.NewsModuls
import com.demo.newsapplication.models.Source
import timber.log.Timber

@Database(entities = [Article::class, NewsModuls::class], version = 1)
@TypeConverters(SourceTypeConverters::class , ArticleTypeConvertor::class )

abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(roomCallback).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }

        private var roomCallback: Callback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                Timber.e("onCreate")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                Timber.e("onOpen")
            }
        }

    }
}