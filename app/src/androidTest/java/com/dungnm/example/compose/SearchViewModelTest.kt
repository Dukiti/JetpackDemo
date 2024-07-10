package com.dungnm.example.compose

import com.dungnm.example.compose.network.repo.GithubRepo
import com.dungnm.example.compose.ui.activity.search.SearchViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject


@HiltAndroidTest
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)

    @Inject
    lateinit var githubRepo: GithubRepo

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = SearchViewModel(githubRepo)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun test() {
        Assert.assertTrue(viewModel.validUser("admin"))
        Assert.assertFalse(viewModel.validUser("admi"))
    }
}