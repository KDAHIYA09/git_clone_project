package com.example.gitclone.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitclone.dao.repositories_dao
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import android.content.Context
import androidx.room.Room

@Database(entities = [RepositoriesDataClass::class], version = 1, exportSchema = false)
abstract class repositries_database : RoomDatabase(){

    // DAO for repositories
    abstract fun repositoriesDao(): repositories_dao

    companion object {

        private const val database_name = "repositories_database"

        @Volatile
        private var INSTANCE: repositries_database? = null // Correct variable name

        // Singleton pattern to create a single instance of the database
        fun getDatabaseInstance(context: Context): repositries_database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    repositries_database::class.java,
                    database_name
                ).build()
                INSTANCE = instance // Correctly assign to INSTANCE
                instance
            }
        }
    }

}