package com.example.gitclone.model_class

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)

data class Repository(
    val name: String,
    val description: String?,
    val language: String?,
    @SerializedName("stargazers_count") val stargazers_count: Int,
    @SerializedName("updated_at") val updated_at: String,
    val owner: Owner,
    @SerializedName("html_url") val html_url: String
)

data class Owner(
    val login: String,
    @SerializedName("avatar_url") val avatar_url: String
)

data class Contributor(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val contributions: Int
)

data class Owner_ContributorRepoCard_modelData(
    @SerializedName("login") val login: String, // Username of the repo owner
    @SerializedName("avatar_url") val avatarUrl: String // URL for the owner's profile image
)

data class ContributorRepoCard_modelData(
    @SerializedName("name") val name: String, // Repo name
    @SerializedName("description") val description: String? = null, // Repo description
    @SerializedName("owner") val owner: Owner // Owner details
)

