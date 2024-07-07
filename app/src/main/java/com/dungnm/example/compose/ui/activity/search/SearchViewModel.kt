package com.dungnm.example.compose.ui.activity.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dungnm.example.compose.model.response.RepoEntity
import com.dungnm.example.compose.model.response.SearchResponse
import com.dungnm.example.compose.network.repo.GithubRepo
import com.dungnm.example.compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepo: GithubRepo
) : BaseViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _listRepo = MutableStateFlow(listOf<RepoEntity>())
    val listRepo: StateFlow<List<RepoEntity>> = _listRepo

    private val _pageIndex = MutableStateFlow(1)
    val pageIndex: StateFlow<Int> = _pageIndex

    init {
        viewModelScope.launch {
            _searchText.debounce(300).collectLatest { input ->
                if (input.isBlank()) {
                    return@collectLatest
                }
                _pageIndex.value = 1
                getSearchResults(input)
            }
        }
    }

    fun onSearch(query: String) {
        _searchText.value = query
    }

    fun onPrePage() {
        viewModelScope.launch {
            val currentPage = _pageIndex.value
            if (currentPage <= 1) {
                return@launch
            }
            _pageIndex.value = (currentPage - 1).coerceAtLeast(1)
            getSearchResults(_searchText.value)
        }
    }

    fun onNextPage() {
        viewModelScope.launch {
            _pageIndex.value += 1
            getSearchResults(_searchText.value)
        }
    }


    private suspend fun getSearchResults(searchKey: String?) {
        try {
            val res = githubRepo.search(searchKey ?: "", pageIndex.value)
            Log.e("123123", "searchRepoGithub: ${res.items.size}")
            _listRepo.value = res.items
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}