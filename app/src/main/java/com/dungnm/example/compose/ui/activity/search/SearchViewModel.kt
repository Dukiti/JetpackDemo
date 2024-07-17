package com.dungnm.example.compose.ui.activity.search

import android.util.Log
import com.dungnm.example.compose.model.response.RepoEntity
import com.dungnm.example.compose.model.response.SearchResponse
import com.dungnm.example.compose.network.repo.IGithubRepo
import com.dungnm.example.compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepo: IGithubRepo
) : BaseViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _listRepo = MutableStateFlow(listOf<RepoEntity>())
    val listRepo: StateFlow<List<RepoEntity>> = _listRepo

    private val _pageIndex = MutableStateFlow(1)
    val pageIndex: StateFlow<Int> = _pageIndex

    private val _loadPage = MutableStateFlow(false)
    val loadPage: StateFlow<Boolean> = _loadPage

    fun initialize() = mainScope.launch {
        Log.e("1239123879012", "initialize: 1239123879012")
        _searchText.debounce(300).collectLatest { input ->
            Log.e("1239123879012", "initialize: input")
            if (input.isBlank()) {
                return@collectLatest
            }
            _pageIndex.value = 1
            getSearchResults(input, 1)
        }
    }

    fun onSearch(query: String) {
        _searchText.value = query
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
        Log.e("21387692", "onNextPage: ${Thread.currentThread().name} -- ${Thread.currentThread().id}")
        getSearchResults(_searchText.value, _pageIndex.value + 1)
    }


    private suspend fun getSearchResults(searchKey: String?, pageIndex: Int) {
        try {
            _loadPage.value = true
            val res = githubRepo.search(searchKey ?: "", pageIndex)
            Log.e("123123", "searchRepoGithub: ${res.items.size}")
            _listRepo.value = res.items
            _pageIndex.value = pageIndex
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            _loadPage.value = false
        }
    }


    val resAll = MutableStateFlow<List<SearchResponse>>(emptyList())
    fun getDefault() = launch {
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