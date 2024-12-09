package com.example.gitclone.api_end_points

import com.example.gitclone.model_class.Contributor
import com.example.gitclone.model_class.ContributorRepoCard_modelData
import com.example.gitclone.model_class.RepositoryResponse
import com.example.gitclone.recyclerview_class_package.data_class_model.ContributorRepoCardData
import com.example.gitclone.recyclerview_class_package.data_class_model.RepositoriesDataClass
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers

interface GitHubApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int, // Add page parameter for pagination
        @Query("per_page") perPage: Int // Add per_page parameter for limiting results per page
    ): Response<RepositoryResponse>

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int, // Add page parameter for pagination
        @Query("per_page") perPage: Int // Add per_page parameter for limiting results per page
    ): Response<List<Contributor>>


    @GET("users/{login}/repos")
    suspend fun getRepositoriesByContributor(@Path("login") login: String): Response<List<ContributorRepoCard_modelData>>


    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GitHubApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(GitHubApiService::class.java)
        }
    }
}
