package com.dungnm.example.compose.home.ui.detail

import com.dungnm.example.compose.core.base.BaseViewModel
import com.dungnm.example.compose.home.model.res.RepoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RepoDetailViewModel : BaseViewModel() {

    private val _itemDetail: MutableStateFlow<RepoEntity?> = MutableStateFlow(null)
    val itemDetail: StateFlow<RepoEntity?> = _itemDetail

    fun setItemDetail(item: RepoEntity) {
        _itemDetail.value = item
    }
}