package com.example.gitclone.api_end_points

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface repository_api {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") keyword: String
    ): Call<GitHubSearchResponse>
}