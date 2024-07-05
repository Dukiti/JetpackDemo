package com.dungnm.example.compose.network.service

import com.dungnm.example.compose.constants.AppConstants
import com.dungnm.example.compose.model.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubService {

    @GET("search/repositories")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: String,
        @Query("per_page") perPage: String = AppConstants.PER_PAGE_DEFAULT,
    ): SearchResponse

}