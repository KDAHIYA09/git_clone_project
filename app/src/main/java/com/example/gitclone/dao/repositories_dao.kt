package com.example.gitclone.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass

@Dao
interface repositories_dao {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
         fun insertAll(repositories: List<RepositoriesDataClass>)


        @Query("SELECT * FROM repositories WHERE keyword = :keyword")
         fun getRepositoriesByKeyword(keyword: String): List<RepositoriesDataClass>

        @Query("DELETE FROM repositories WHERE keyword = :keyword")
        fun deleteByKeyword(keyword: String)

        @Query("SELECT EXISTS(SELECT 1 FROM repositories WHERE keyword = :keyword)")
        fun doesKeywordExist(keyword: String): Boolean



}