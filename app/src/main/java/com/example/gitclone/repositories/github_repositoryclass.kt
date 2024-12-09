package com.example.gitclone.repositories

import android.util.Log
import com.example.gitclone.api_end_points.GitHubApiService
import com.example.gitclone.model_class.Contributor
import com.example.gitclone.model_class.Repository
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass




class GitHubRepository(
    private val apiService: GitHubApiService
) {

    suspend fun getRepositories(query: String, page: Int, perPage: Int): List<RepositoriesDataClass> {
        val response = apiService.searchRepositories(query, page, perPage)

        if (response.isSuccessful) {
            val repositories = response.body()?.items ?: emptyList()
            return repositories.map { repo ->
                RepositoriesDataClass(
                    keyword = query,
                    name = repo.name,
                    description = repo.description,
                    language = repo.language,
                    stars = repo.stargazers_count,
                    updatedAt = repo.updated_at,
                    profileImageUrl = repo.owner.avatar_url,
                    ownerName = repo.owner.login,
                    projectUrl = repo.html_url
                )
            }
        } else {
            throw Exception("Failed to load repositories: ${response.message()}")
        }
    }

    // Fetch contributors for a repository
    suspend fun getContributors(owner: String, repo: String, page: Int = 1, perPage: Int = 10): List<Contributor> {
        val response = apiService.getContributors(owner, repo, page, perPage)

        if (response.isSuccessful) {
            val contributors = response.body() ?: emptyList()
            Log.d("GitHubRepository", "Fetched contributors: $contributors")  // Log the contributors list
            return contributors.map { contributor ->
                Contributor(
                    id = contributor.id,
                    login = contributor.login,
                    avatarUrl = contributor.avatarUrl ?: " ",
                    contributions = contributor.contributions
                )
            }
        } else {
            Log.e("GitHubRepository", "Failed to fetch contributors: ${response.message()}")  // Log error message
            throw Exception("Failed to fetch contributors: ${response.message()}")
        }
    }

//    suspend fun getRepositoriesByContributor(login: String): List<ContributorRepoCardData> {
//        val response = apiService.getRepositoriesByContributor(login)
//        if (response.isSuccessful) {
//            val repositories = response.body() ?: emptyList()
//            return repositories.map { repo ->
//                ContributorRepoCardData(
//                    repoName = repo.repoName,
//                    repoDescription = repo.repoDescription,
//                    profileImageUrl = repo.profileImageUrl,
//                    ownerName = repo.ownerName
//                )
//            }
//        } else {
//            throw Exception("Failed to fetch repositories for contributor: ${response.message()}")
//        }
//    }



}
