package com.dungnm.example.core.mock

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        runBlocking {
            delay(1500) // Delay for 1.5 seconds
        }

        val uri = chain.request().url.toUri().toString()

        // Extract the function name from the request URL by removing the base URL
        val baseURL = ""
        var function = uri.replace(baseURL, "")
        function = function.removePrefix("/")

        var mockDir: String = ""

        // Split the function name by slashes and assign the first part to `mockDir`
        function.split("/").let {
            mockDir = it.first()
        }

        // Construct the file name by replacing the directory name and slashes in the function name with underscores, and appending ".json"
        val fileName = function.replace("$mockDir/", "").replace("/", "_") + ".json"

        // Load the mock response from a JSON file in the assets folder
        // The file is located in a directory specified by `mockDir` and its name is specified by `fileName`
        val responseString = loadJsonFromAsset(
            mockDir = mockDir, fileName = fileName, context = context
        )

        return Response.Builder().code(200).message(responseString).request(chain.request())
            .protocol(Protocol.HTTP_1_0).body(
                responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull())
            ).addHeader("content-type", "application/json").build()
    }

    private fun loadJsonFromAsset(mockDir: String, fileName: String, context: Context): String {
        val filePath = "mockapi/$mockDir/$fileName"
        return try {
            context.assets.open(filePath).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            return "Failed to open asset file mockup: $fileName"
        }
    }
}