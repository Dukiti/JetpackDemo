package com.dungnm.example.compose.network.repo

import android.util.Log
import com.dungnm.example.compose.model.response.SearchResponse
import com.dungnm.example.compose.network.service.GithubService
import javax.inject.Inject

class GithubRepo @Inject constructor(private val githubComposeService: GithubService) {

    suspend fun search(query: String, page: Int): SearchResponse {
        Log.e("123123", "search: query:$query page:$page")
        Log.e("12391237", "GithubRepo: ${Thread.currentThread().id}", )
        return githubComposeService.search(query, "$page")
    }
}