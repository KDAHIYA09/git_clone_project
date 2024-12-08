package com.example.gitclone.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitclone.dao.repositories_dao
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import kotlinx.coroutines.launch

class repositories_RepositoryClass(private val repositoriesDao: repositories_dao) {

    // Insert repositories
    suspend fun insertRepositories(repositories: List<RepositoriesDataClass>) {
        repositoriesDao.insertAll(repositories)
        Log.d("Database", "Inserted ${repositories.size} repositories")
    }


    // Get repositories by keyword
    suspend fun getRepositoriesForKeyword(keyword: String): List<RepositoriesDataClass> {
        return repositoriesDao.getRepositoriesByKeyword(keyword)
    }

    suspend fun deleteRepositoriesForKeyword(keyword: String) {
        repositoriesDao.deleteByKeyword(keyword)
    }

    // Check if a keyword exists
    suspend fun doesKeywordExist(keyword: String): Boolean {
        return repositoriesDao.doesKeywordExist(keyword)
    }
}

