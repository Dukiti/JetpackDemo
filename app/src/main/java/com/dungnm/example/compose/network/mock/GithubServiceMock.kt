package com.dungnm.example.compose.network.mock

import android.content.Context
import android.util.Log
import com.dungnm.example.compose.network.service.SEARCH_PATH
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class GithubServiceMock : IServiceMock {

    override val endPointList: List<String> = listOf(SEARCH_PATH)

    override fun process(context: Context, request: Request): Response? {
        Log.e("12391237", "mock: ${Thread.currentThread().id}", )
        var response: Response? = null
        val httpUrl = request.url
        if (httpUrl.toString().contains(SEARCH_PATH)) {
            val dataQuery = httpUrl.queryParameter("q")
            Log.e("123123", "dataQuery: $dataQuery", )
            if (dataQuery == "a") {
                var path: String = httpUrl.toUri().path
                path = if (path.startsWith('/')) path.substring(1) else path
                val responseString= getJsonDataFromAsset(context, path).orEmpty()
                response = Response.Builder().code(200).message(responseString).request(request)
                    .protocol(Protocol.HTTP_1_0).body(
                        ResponseBody.create(
                            "application/json".toMediaType(), responseString.toByteArray()
                        )
                    ).addHeader("content-type", "application/json").build()
            } else {
                response = Response.Builder().code(599).build()
            }
        }
        return response
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            // Append the json extension
            jsonString =
                context.assets.open("$fileName.json").bufferedReader().use { it.readText() }
        } catch (ioException: Exception) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}