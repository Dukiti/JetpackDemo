package com.dungnm.example.compose.network.repo.github

import android.content.Context
import com.dungnm.example.compose.model.response.SearchResponse
import com.dungnm.example.compose.network.repo.IGithubRepo
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class MockGithubRepo @Inject constructor(
    @ApplicationContext private val context: Context
) : IGithubRepo {
    override suspend fun search(query: String, page: Int): SearchResponse {
        var response: SearchResponse? = null
        if (query == "load_data") {
            val jsonData = getJsonDataFromAsset(context, "search/repositories.json")
            response = Gson().fromJson(jsonData, SearchResponse::class.java)
        } else if (query == "max_page") {
            val jsonData = getJsonDataFromAsset(context, "search/repositories_last_page.json")
            response = Gson().fromJson(jsonData, SearchResponse::class.java)
        } else if (query == "load_fail") {
            response = null
        }
        if (response == null) {
            throw Exception("data not valid")
        } else {
            return response
        }
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}