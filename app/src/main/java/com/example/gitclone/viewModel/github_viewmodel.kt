package com.example.gitclone.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitclone.model_class.Contributor

import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import com.example.gitclone.repositories.GitHubRepository
import com.example.gitclone.repositories.repositories_RepositoryClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GitHubViewModel(
    private val apiRepository: GitHubRepository
) : ViewModel() {

    val contributors = MutableLiveData<List<Contributor>>()
    val loading = MutableLiveData<Boolean>()
    val loading1 = MutableLiveData<Boolean>()
    val loading2 = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private val _repositories = MutableLiveData<List<RepositoriesDataClass>>()
    val repositories: LiveData<List<RepositoriesDataClass>> get() = _repositories



    private var currentPage = 1
    private var isLoadingNextPage = false

    // Getter for currentPage
    fun getCurrentPage(): Int {
        return currentPage
    }

    // Fetch repositories with pagination support
    fun fetchRepositories(query: String, page: Int = currentPage, perPage: Int = 15) {
        if (isLoadingNextPage) return  // Avoid making multiple requests

        isLoadingNextPage = true
        loading.value = true // Show progress bar

        viewModelScope.launch {
            try {
                val repositoriesData = apiRepository.getRepositories(query, page, perPage)
                val currentList = _repositories.value?.toMutableList() ?: mutableListOf()
                currentList.addAll(repositoriesData)  // Append new data

                _repositories.postValue(currentList)  // Update the LiveData with new data
                currentPage++  // Increment the page number for the next fetch
                isLoadingNextPage = false
                loading.value = false  // Hide progress bar
            } catch (e: Exception) {
                isLoadingNextPage = false
                loading.value = false
                error.postValue("Failed to load repositories: ${e.message}")
            }
        }
    }

    // Fetch contributors for a specific repository
    fun fetchContributors(owner: String, repo: String, page: Int = 1, perPage: Int = 10) {
        loading1.value = true  // Show progress bar
        viewModelScope.launch {
            try {
                val apiResults = apiRepository.getContributors(owner, repo, page, perPage)
                loading1.value = false
                if (apiResults.isNullOrEmpty()) {
                    // If no contributors found, set the LiveData to empty list
                    contributors.postValue(emptyList())
                } else {
                    contributors.postValue(apiResults)  // Update LiveData with contributors
                }
            } catch (e: Exception) {
                loading1.value = false
                error.postValue("Failed to load contributors: ${e.message}")
            }
        }
    }

    // Fetch repositories for a specific contributor
//    fun fetchRepositoriesForContributor(login: String) {
//        loading2.value = true // Show loading indicator
//
//        viewModelScope.launch {
//            try {
//                // Fetch repositories for the contributor using their login
//                val repositoriesData = apiRepository.getRepositoriesByContributor(login)
//
//                // Map ContributorRepoCard_modelData to ContributorRepoCardData
//                val mappedRepositories = repositoriesData.map { repo ->
//                    ContributorRepoCardData(
//                        profileImageUrl = repo.profileImageUrl,
//                        ownerName = repo.ownerName,
//                        repoName = repo.repoName,
//                        repoDescription = repo.repoDescription
//                    )
//                }
//
//                _repositories_of_contribter.value = mappedRepositories // Set the repositories LiveData
//                loading2.value = false // Hide loading indicator
//            } catch (e: Exception) {
//                loading2.value = false // Hide loading indicator
//                error.value = "Failed to load repositories for contributor: ${e.message}"
//            }
//        }
//    }



}
