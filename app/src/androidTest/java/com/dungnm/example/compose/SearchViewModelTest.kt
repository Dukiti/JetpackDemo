package com.dungnm.example.compose

import android.app.Application
import android.content.Context
import com.dungnm.example.compose.model.response.RepoEntity
import com.dungnm.example.compose.model.response.SearchResponse
import com.dungnm.example.compose.network.repo.GithubRepo
import com.dungnm.example.compose.ui.activity.search.SearchViewModel
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject


@HiltAndroidTest
class SearchViewModelTest {
    private val hiltRule = HiltAndroidRule(this)
    private val coroutineRule = MainCoroutineRule()
    private val mockWebServer = MockWebServer()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(coroutineRule)

    @Inject
    lateinit var githubRepo: GithubRepo

    @Inject
    lateinit var context: Application

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        mockWebServer.start()
        hiltRule.inject()
        viewModel = SearchViewModel(githubRepo)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSearch() = runTest {
        viewModel.onSearch("a")
        advanceUntilIdle()
        launch {
            viewModel.listRepo.collect {
                Assert.assertEquals(it.size, 10)
                Assert.assertTrue(viewModel.searchText.value == "a")
                Assert.assertTrue(viewModel.pageIndex.value == 1)
            }
        }

    }

    @Test
    fun testSuccessfulDataFetch() = runTest {
//        val mockData = getJsonDataFromAsset(context, "search/repositories.json")
//        val mockResponse = MockResponse()
//            .setResponseCode(200)
//            .setBody("""["Item1", "Item2", "Item3"]""")
//        mockWebServer.enqueue(mockResponse)
//
//        val states = mutableListOf<MyState>()
//        val job = launch {
//            viewModel.stateFlow.toList(states)
//        }
//
//        viewModel.onSearch("a")
//
//        assertEquals(10, states.size)
//
//        job.cancel()
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