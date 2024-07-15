package com.dungnm.example.compose.network.repo.github

import com.dungnm.example.compose.model.response.SearchResponse

interface IGithubRepo {
    suspend fun search(query: String, page: Int): SearchResponse
}