package com.dungnm.example.compose

import com.dungnm.example.compose.network.repo.github.IGithubRepo
import com.dungnm.example.compose.ui.activity.search.SearchViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
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
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        Assert.assertEquals(10, viewModel.listRepo.value.size)
        Assert.assertEquals("load_data", viewModel.searchText.value)
        Assert.assertEquals(1, viewModel.pageIndex.value)
    }

    @Test
    fun testLoadDataFail() = runTest {
        viewModel.onSearch("load_fail")
        advanceUntilIdle()
        Assert.assertEquals(0, viewModel.listRepo.value.size)
        Assert.assertEquals("load_fail", viewModel.searchText.value)
        Assert.assertEquals(1, viewModel.pageIndex.value)
    }

    @Test
    fun testLoadDataMaxPage() = runTest {
        viewModel.onSearch("max_page")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.listRepo.value.size < 10)
    }

    @Test
    fun testOnNextPageSuccess() = runTest {
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onNextPage()
        advanceUntilIdle()
        Assert.assertEquals(currentPage + 1, viewModel.pageIndex.value)
    }

    @Test
    fun testOnPrePageSuccess() = runTest {
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onNextPage()
        viewModel.onNextPage()
        viewModel.onPrePage()
        advanceUntilIdle()
        Assert.assertEquals(currentPage + 1, viewModel.pageIndex.value)
    }

    @Test
    fun testOnPrePageFail() = runTest {
        viewModel.onSearch("load_data")
        advanceUntilIdle()
        val currentPage = viewModel.pageIndex.value
        viewModel.onPrePage()
        advanceUntilIdle()
        Assert.assertEquals(currentPage, viewModel.pageIndex.value)
    }

}