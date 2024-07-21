package com.dungnm.example.compose.home.ui.search

import android.util.Log
import com.dungnm.example.compose.core.base.BaseViewModel
import com.dungnm.example.compose.home.model.res.RepoEntity
import com.dungnm.example.compose.home.model.res.SearchResponse
import com.dungnm.example.compose.home.repo.IGithubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    internal lateinit var githubRepo: IGithubRepo

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _listRepo = MutableStateFlow(listOf<RepoEntity>())
    val listRepo: StateFlow<List<RepoEntity>> = _listRepo

    private val _pageIndex = MutableStateFlow(1)
    val pageIndex: StateFlow<Int> = _pageIndex

    private val _loadPage = MutableStateFlow(false)
    val loadPage: StateFlow<Boolean> = _loadPage

    fun onSearch(query: String) {
        _searchText.value = query
    }

    fun onQuery(query: String) = launchSilent {
        if (query.isBlank()) {
            return@launchSilent
        }
        _searchText.value = query
        _pageIndex.value = 1
        getSearchResults(query, 1)
    }

    fun onPrePage() = launchSilent {
        val currentPage = _pageIndex.value
        if (currentPage <= 1) {
            return@launchSilent
        }
        val pageIndex = (currentPage - 1).coerceAtLeast(1)
        getSearchResults(_searchText.value, pageIndex)
    }


    fun onNextPage() = launchSilent {
        Log.e(
            "21387692", "onNextPage: ${Thread.currentThread().name} -- ${Thread.currentThread().id}"
        )
        getSearchResults(_searchText.value, _pageIndex.value + 1)
    }


    private suspend fun getSearchResults(searchKey: String?, pageIndex: Int) {
        try {
            _loadPage.value = true
            val res = githubRepo.search(searchKey ?: "", pageIndex)
            Log.e("123123", "searchRepoGithub: ${res.items.size}")
            _listRepo.value = res.items
            _pageIndex.value = pageIndex
        } finally {
            _loadPage.value = false
        }
    }


    val resAll = MutableStateFlow<List<SearchResponse>>(emptyList())
    fun getConfig() = launch {
        val job1 = async<SearchResponse> {
            githubRepo.search("load_data", 1)
        }
        val job2 = async {
            githubRepo.search("load_data", 1)
        }
        val job3 = async {
            githubRepo.search("load_data", 1)
        }
        val listJob = listOf(job1, job2, job3)
        resAll.value = listJob.awaitAll()
    }
}