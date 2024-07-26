package com.dungnm.example.compose.core.navigation

import androidx.navigation.NavHostController
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NavEntryPoint {
    fun mainNav(): NavHostController
}