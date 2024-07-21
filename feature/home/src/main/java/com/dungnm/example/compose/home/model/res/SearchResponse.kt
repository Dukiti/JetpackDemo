package com.dungnm.example.compose.home.model.res

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName

@Stable
data class SearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepoEntity>
)

@Stable
data class RepoEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("owner") val owner: OwnerEntity?,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stargazersCount: String,
    @SerializedName("watchers_count") val watchersCount: String,
    @SerializedName("language") val language: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)

@Stable
data class OwnerEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("login") val login: String?,
)