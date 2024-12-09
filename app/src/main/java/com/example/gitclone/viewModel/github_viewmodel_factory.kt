package com.example.gitclone.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitclone.repositories.GitHubRepository
import com.example.gitclone.repositories.repositories_RepositoryClass

class GitHubViewModelFactory(
private val apiRepository: GitHubRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
            return GitHubViewModel(apiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//
//class GitHubViewModelFactory(
//    private val apiRepository: GitHubRepository,
//    private val dbRepository: repositories_RepositoryClass
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
//            return GitHubViewModel(apiRepository, dbRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}



//class GitHubViewModelFactory(
//    private val repository: GitHubRepository
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return GitHubViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}
