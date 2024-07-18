package com.dungnm.example.compose.ui.activity.detail

import androidx.compose.runtime.mutableStateOf
import com.dungnm.example.compose.model.response.RepoEntity
import com.dungnm.example.compose.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RepoDetailViewModel : BaseViewModel() {

    private val _itemDetail: MutableStateFlow<RepoEntity?> = MutableStateFlow(null)
    val itemDetail: StateFlow<RepoEntity?> = _itemDetail

    fun setItemDetail(item: RepoEntity) {
        _itemDetail.value = item
    }
}