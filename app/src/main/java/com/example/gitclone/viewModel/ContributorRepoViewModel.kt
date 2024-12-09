package com.example.gitclone.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitclone.api_end_points.GitHubApiService
import com.example.gitclone.recyclerview_class_package.data_class_model.ContributorRepoCardData
import kotlinx.coroutines.launch
import com.example.gitclone.model_class.ContributorRepoCard_modelData
import com.example.gitclone.model_class.Owner_ContributorRepoCard_modelData
import com.example.gitclone.recyclerview_class_package.data_class_model.Contributor



class ContributorRepoViewModel(private val apiService: GitHubApiService) : ViewModel() {

    private val _repoList = MutableLiveData<List<ContributorRepoCardData>>()
    val repoList: LiveData<List<ContributorRepoCardData>> get() = _repoList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchRepositories(login: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getRepositoriesByContributor(login)
                if (response.isSuccessful) {
                    val body = response.body() ?: emptyList()
                    _repoList.value = body.map {
                        ContributorRepoCardData(
                            repoName = it.name,
                            repoDescription = it.description,
                            ownerName = it.owner.login, // The 'login' field from the owner object
                            profileImageUrl = it.owner.avatar_url// The 'avatar_url' from the owner object
                        )
                    }
                } else {
                    _error.value = "Failed to fetch repositories"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


}
