package com.dungnm.example.compose.home.repo

import com.dungnm.example.compose.home.model.res.SearchResponse

interface IGithubRepo {
    suspend fun search(query: String, page: Int): SearchResponse
}