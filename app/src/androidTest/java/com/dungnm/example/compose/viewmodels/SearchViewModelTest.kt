package com.dungnm.example.compose.viewmodels

import android.content.Context
import com.dungnm.example.compose.MainCoroutineRule
import com.dungnm.example.compose.network.repo.IGithubRepo
import com.dungnm.example.compose.network.repo.github.GithubRepo
import com.dungnm.example.compose.network.repo.github.MockGithubRepo
import com.dungnm.example.compose.network.service.GithubService
import com.dungnm.example.compose.runBlockingTest
import com.dungnm.example.compose.ui.activity.search.SearchViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(coroutineRule)

    @Inject
    lateinit var githubRepo: IGithubRepo

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = SearchViewModel(githubRepo)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testLoadDataSuccess() = runTest {
        viewModel.initialize()
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        assertEquals(10, viewModel.listRepo.value.size)
        assertEquals("load_data", viewModel.searchText.value)
        assertEquals(1, viewModel.pageIndex.value)
    }

    @Test
    fun testLoadDataFail() = runTest {
        viewModel.onSearch("load_fail")
        advanceUntilIdle()
        assertEquals(0, viewModel.listRepo.value.size)
        assertEquals("load_fail", viewModel.searchText.value)
        assertEquals(1, viewModel.pageIndex.value)
    }

    @Test
    fun testLoadDataMaxPage() = runTest {
        viewModel.initialize()
        viewModel.onSearch("max_page")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.listRepo.value.size in 1..9)
    }

    @Test
    fun testOnNextPageSuccess() = runTest {
        viewModel.initialize()
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onNextPage()
        advanceUntilIdle()
        assertEquals(currentPage + 1, viewModel.pageIndex.value)
    }

    @Test
    fun testOnPrePageSuccess() = runTest {
        viewModel.initialize()
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onNextPage().join()
        viewModel.onNextPage().join()
        viewModel.onPrePage().join()
        advanceUntilIdle()
        assertEquals(currentPage + 1, viewModel.pageIndex.value)
    }

    @Test
    fun testOnPrePageFail() = runTest {
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onPrePage()
        advanceUntilIdle()
        assertEquals(currentPage, viewModel.pageIndex.value)
    }

    @Test
    fun repoInitWorksAndDataIsHelloWorld() = runTest {
        viewModel.getDefault().join()
        advanceUntilIdle()
        assertEquals(3, viewModel.resAll.value.size)
    }

}