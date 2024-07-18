package com.dungnm.example.compose.ui.activity.gallery

import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.viewModelScope
import com.dungnm.example.compose.model.ImageGallery
import com.dungnm.example.compose.model.PlaceholderState
import com.dungnm.example.compose.network.repo.GalleryRepo
import com.dungnm.example.compose.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor() : BaseViewModel() {


    private val _listPhotoSF = MutableStateFlow(emptyList<ImageGallery>())
    private val _loadingStateSF = MutableStateFlow<PlaceholderState>(PlaceholderState.Idle(true))
    private val _firstPageStateSF = MutableStateFlow<PlaceholderState>(PlaceholderState.Idle(true))
    private val _isRefreshingSF = MutableStateFlow(false)

    private var loadedAllPage = false

    private inline val shouldLoadNextPage: Boolean
        get() {
            val isFirst = if (pageIndex == 0) {
                _firstPageStateSF.value is PlaceholderState.Idle
            } else {
                _loadingStateSF.value is PlaceholderState.Idle
            }
            return isFirst && !loadedAllPage
        }

    private inline val shouldRetry: Boolean
        get() = if (pageIndex == 0) {
            _firstPageStateSF.value is PlaceholderState.Failure
        } else {
            _loadingStateSF.value is PlaceholderState.Failure
        }

    val galleryStateFlow: StateFlow<List<ImageGallery>> = _listPhotoSF.asStateFlow()

    val firstPageStateFlow: StateFlow<PlaceholderState> = _firstPageStateSF.asStateFlow()

    val loadingStateFlow: StateFlow<PlaceholderState> = _loadingStateSF.asStateFlow()

    private var pageIndex = 0
    private val pageLimit = 10

    @MainThread
    fun loadNextPage(context: Context) {
        if (shouldLoadNextPage) {
            loadPageInternal(context)
        }
    }

    @MainThread
    fun retry(context: Context) {
        if (shouldRetry) {
            loadPageInternal(context)
        }
    }

    @MainThread
    fun refresh(context: Context) {
        loadPageInternal(context, refresh = true)
    }

    @MainThread
    private fun updateState(state: PlaceholderState) {
        if (pageIndex == 0) {
            _firstPageStateSF.value = state
        } else {
            _loadingStateSF.value = state
        }
    }

    private fun loadPageInternal(context: Context, refresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            if (refresh) {
                _isRefreshingSF.value = true
                pageIndex = 0
            } else {
                updateState(PlaceholderState.Loading)
            }

            val currentList = if (refresh) emptyList() else _listPhotoSF.value

            runCatching {
                val listResult =
                    GalleryRepo.getListGalleryPhoto(context, null, pageIndex, pageLimit)
                listResult
            }.fold(
                onSuccess = {
                    if (refresh) {
                        _isRefreshingSF.value = false
                    } else {
                        updateState(PlaceholderState.Idle(it.isEmpty()))
                    }
                    _listPhotoSF.value = currentList + it
                    pageIndex++
                    loadedAllPage = it.size < pageLimit
                },
                onFailure = {
                    if (refresh) {
                        _isRefreshingSF.value = false
                    } else {
                        updateState(PlaceholderState.Failure(it))
                    }
                }
            )
        }
    }
}