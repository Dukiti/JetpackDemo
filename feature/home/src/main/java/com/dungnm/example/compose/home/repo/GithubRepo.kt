package com.dungnm.example.compose.home.repo

import android.util.Log
import com.dungnm.example.compose.home.model.res.SearchResponse
import com.dungnm.example.compose.home.network.GithubService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubRepo @Inject constructor(
    private val githubComposeService: GithubService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IGithubRepo {

    override suspend fun search(query: String, page: Int): SearchResponse =
        withContext(ioDispatcher) {
            Log.e(
                "search23912868",
                "search $query: ${Thread.currentThread().name} - ${Thread.currentThread().id}"
            )
            val res = githubComposeService.search(query, "$page", "10")

            Log.e(
                "search23912868",
                "res $query: ${Thread.currentThread().name} - ${Thread.currentThread().id}"
            )
            return@withContext res
        }
}