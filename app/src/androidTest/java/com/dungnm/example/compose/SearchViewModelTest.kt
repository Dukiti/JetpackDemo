package com.dungnm.example.compose

import com.dungnm.example.compose.network.repo.GithubRepo
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


@HiltAndroidTest
class SearchViewModelTest {
    private val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
//        .around(coroutineRule)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var githubRepo: GithubRepo

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = SearchViewModel(githubRepo)
    }

    @After
    fun testDown() {

    }

    @Test
    fun testSearch() = runTest {
        viewModel.onSearch("github")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.searchText.value == "github")
        Assert.assertTrue(viewModel.pageIndex.value == 1)
    }
}