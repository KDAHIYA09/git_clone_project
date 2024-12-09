package com.example.gitclone.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitclone.api_end_points.GitHubApiService

class ContributorRepoViewModelFactory(private val apiService: GitHubApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContributorRepoViewModel::class.java)) {
            return ContributorRepoViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}