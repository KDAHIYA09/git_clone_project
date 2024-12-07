package com.example.gitclone.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitclone.dao.repositories_dao
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import kotlinx.coroutines.launch

class repositories_RepositoryClass(private val repositoriesDao: repositories_dao) {

    /**
     * Insert a list of repositories into the database.
     */
    suspend fun insertRepositories(repositories: List<RepositoriesDataClass>) {
        repositoriesDao.insertAll(repositories)
    }

    /**
     * Retrieve repositories for a given keyword.
     * Returns an empty list if no repositories are found.
     */
    suspend fun getRepositoriesForKeyword(keyword: String): List<RepositoriesDataClass> {
        return repositoriesDao.getRepositoriesByKeyword(keyword).ifEmpty {
            emptyList() // Handle null safety by returning an empty list.
        }
    }

    /**
     * Update repositories for a specific keyword by replacing them with new data.
     * Deletes old data and inserts the new data.
     */
    suspend fun updateRepositoriesByKeyword(keyword: String, repositories: List<RepositoriesDataClass>) {
        repositoriesDao.updateRepositoriesByKeyword(keyword, repositories)
    }

    /**
     * Check if a keyword exists in the database.
     */
    suspend fun doesKeywordExist(keyword: String): Boolean {
        return repositoriesDao.doesKeywordExist(keyword)
    }
}

