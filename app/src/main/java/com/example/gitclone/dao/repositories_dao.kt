package com.example.gitclone.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass

@Dao
interface repositories_dao {

        /**
         * Insert a list of repositories into the database.
         * If a repository with the same ID already exists, it will be replaced.
         */
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertAll(repositories: List<RepositoriesDataClass>)

        /**
         * Retrieve repositories for a specific keyword from the database.
         * @param keyword The keyword to filter repositories by.
         * @return A list of repositories matching the given keyword.
         */
        @Query("SELECT * FROM repositories WHERE keyword = :keyword")
        suspend fun getRepositoriesByKeyword(keyword: String): List<RepositoriesDataClass>

        /**
         * Update existing repositories for a specific keyword by replacing them with new data.
         * This is achieved by deleting existing entries and inserting the new ones.
         * @param keyword The keyword whose repositories need updating.
         * @param repositories The new list of repositories to replace the old data.
         */
        @Transaction
        suspend fun updateRepositoriesByKeyword(keyword: String, repositories: List<RepositoriesDataClass>) {
            deleteRepositoriesByKeyword(keyword)
            insertAll(repositories)
        }

        /**
         * Delete repositories associated with a specific keyword.
         * @param keyword The keyword to filter repositories for deletion.
         */
        @Query("DELETE FROM repositories WHERE keyword = :keyword")
        suspend fun deleteRepositoriesByKeyword(keyword: String)

        /**
         * Check if any repositories exist for a specific keyword.
         * Useful for determining if data is available offline.
         * @param keyword The keyword to check.
         * @return True if repositories exist for the given keyword, false otherwise.
         */
        @Query("SELECT EXISTS(SELECT 1 FROM repositories WHERE keyword = :keyword)")
        suspend fun doesKeywordExist(keyword: String): Boolean

}