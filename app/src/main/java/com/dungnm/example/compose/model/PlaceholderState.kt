package com.dungnm.example.compose.model

sealed class PlaceholderState {
    data class Idle(val isEmpty: Boolean) : PlaceholderState()
    object Loading : PlaceholderState()
    data class Failure(val throwable: Throwable) : PlaceholderState()
}