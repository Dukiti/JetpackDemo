package com.dungnm.example.compose.ui.activity.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dungnm.example.compose.network.repo.GithubRepo
import com.dungnm.example.compose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepo: GithubRepo
) : BaseViewModel() {

    private val currentPage = 1

    fun searchRepoGithub(searchKey: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = githubRepo.search(searchKey ?: "", currentPage)
                Log.e("123123", "searchRepoGithub: ${res.items.size}")
            } catch (e: Exception) {

            }
        }
    }
}