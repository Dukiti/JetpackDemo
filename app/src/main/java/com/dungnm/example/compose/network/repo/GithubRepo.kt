package com.dungnm.example.compose.network.repo

import com.dungnm.example.compose.model.response.SearchResponse
import com.dungnm.example.compose.network.service.GithubService
import javax.inject.Inject

class GithubRepo @Inject constructor(private val githubComposeService: GithubService) {

    suspend fun search(query: String, page: Int): SearchResponse {
        return githubComposeService.search(query, "$page")
    }
}