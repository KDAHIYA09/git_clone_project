package com.example.gitclone.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import com.example.gitclone.repositories.repositories_RepositoryClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RepositoryViewModel(private val repository: repositories_RepositoryClass) : ViewModel() {

    private val _repositories = MutableLiveData<List<RepositoriesDataClass>>()
    val repositories: LiveData<List<RepositoriesDataClass>> get() = _repositories

    private val _noDataAvailable = MutableLiveData<Boolean>()
    val noDataAvailable: LiveData<Boolean> get() = _noDataAvailable

    /**
     * Fetch repositories for the given keyword.
     * Handles the case when no data is available.
     */
    fun fetchRepositories(keyword: String) {
        viewModelScope.launch {
            try {
                // Switch to IO Dispatcher for database operations
                val data = withContext(Dispatchers.IO) {
                    repository.getRepositoriesForKeyword(keyword)
                }

                if (data.isEmpty()) {
                    // No data available
                    _noDataAvailable.postValue(true)
                    _repositories.postValue(emptyList())
                } else {
                    // Data available
                    _noDataAvailable.postValue(false)
                    _repositories.postValue(data)
                }
            } catch (e: Exception) {
                // Handle exceptions if any
                e.printStackTrace()
            }
        }
    }

    fun addRepositories(repositoriesList: List<RepositoriesDataClass>) {
        viewModelScope.launch {
            try {
                // Switch to IO Dispatcher for database operations
                withContext(Dispatchers.IO) {
                    repository.insertRepositories(repositoriesList) // Call the insert function in the repository
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Update repositories for a given keyword (useful for syncing new data).
     */
//    fun updateRepositories(keyword: String, repositories: List<RepositoriesDataClass>) {
//        viewModelScope.launch {
//            repository.updateRepositoriesByKeyword(keyword, repositories)
//        }
//    }

    /**
     * Delete repositories for the given keyword.
     */
//    fun deleteRepositories(keyword: String) {
//        viewModelScope.launch {
//            repository.deleteRepositoriesByKeyword(keyword)
//        }
//    }

    /**
     * Check if repositories exist for the given keyword.
     */
    fun doesKeywordExist(keyword: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val exists = repository.doesKeywordExist(keyword)
            onResult(exists)
        }
    }
}
