package com.dungnm.example.compose.home.viewmodel

import com.dungnm.example.compose.home.MainCoroutineRule
import com.dungnm.example.compose.home.repo.IGithubRepo
import com.dungnm.example.compose.home.ui.search.SearchViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
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
    private val hiltRule = HiltAndroidRule(this)
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(coroutineRule)

    @Inject
    lateinit var githubRepo: IGithubRepo

    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = SearchViewModel().apply {
            this.githubRepo = this@SearchViewModelTest.githubRepo
        }
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testLoadDataSuccess() = runTest {
        viewModel.onQuery("load_data")
        advanceUntilIdle()
        assertEquals(10, viewModel.listRepo.value.size)
        assertEquals("load_data", viewModel.searchText.value)
        assertEquals(1, viewModel.pageIndex.value)
    }

    @Test
    fun testLoadDataFail() = runTest {
        viewModel.onQuery("load_fail")
        advanceUntilIdle()
        assertEquals(0, viewModel.listRepo.value.size)
        assertEquals("load_fail", viewModel.searchText.value)
        assertEquals(1, viewModel.pageIndex.value)
    }

    @Test
    fun testLoadDataMaxPage() = runTest {
        viewModel.onQuery("max_page")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.listRepo.value.size in 1..9)
    }

    @Test
    fun testOnNextPageSuccess() = runTest {
        viewModel.onQuery("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onNextPage()
        advanceUntilIdle()
        assertEquals(currentPage + 1, viewModel.pageIndex.value)
    }

    @Test
    fun testOnPrePageSuccess() = runTest {
        viewModel.onQuery("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onNextPage()
        viewModel.onNextPage()
        viewModel.onPrePage()
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
    fun testSynchronized() = runTest {
        viewModel.getConfig()
        advanceUntilIdle()
        assertEquals(3, viewModel.resAll.value.size)
    }

}