package com.example.gitclone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gitclone.dao.repositories_dao
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import android.util.Log

@Database(entities = [RepositoriesDataClass::class], version = 1, exportSchema = false)
abstract class repositries_database : RoomDatabase() {
    abstract fun repositoriesDao(): repositories_dao

    companion object {
        @Volatile private var INSTANCE: repositries_database? = null

        fun getDatabaseInstance(context: Context): repositries_database {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    repositries_database::class.java,
                    "repositories_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

