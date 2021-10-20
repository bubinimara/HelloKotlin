package com.example.hellokotlin.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.hellokotlin.data.model.Movie


/**
 *
 * Created by Davide Parise on 21/09/21.
 */

@Database(entities = [Movie::class,Movie.AccountState::class], version = 3, exportSchema = false)
@AutoMigration(from = 0,to = 1)
abstract class AppDb: RoomDatabase() {
    abstract fun movieDao() :MovieDao

    companion object{
        private var INSTANCE: AppDb ?= null

        fun getDb(context: Context):AppDb{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDb::class.java,"app_db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return
                instance
            }
        }
    }
}