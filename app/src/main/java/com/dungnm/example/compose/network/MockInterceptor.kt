package com.dungnm.example.compose.network

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.net.URI

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response? = null
        val uri: URI = chain.request().url.toUri()
        // Get path String.
        var path: String = uri.path
        // Remove the starter slide '/'
        path = if (path.startsWith('/')) path.substring(1) else path
        Log.e("1239172812", "intercept: $path", )
        // Get the json file text
        val responseString: String = getJsonDataFromAsset(context, path).orEmpty()

        // Create the response
        response = Response.Builder()
            .code(200)
            .message(responseString)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()
        return response
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            // Append the json extension
            jsonString =
                context.assets.open("$fileName.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}