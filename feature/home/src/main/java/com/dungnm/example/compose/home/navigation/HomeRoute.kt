package com.dungnm.example.compose.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dungnm.example.compose.core.navigation.IRoute
import com.dungnm.example.compose.home.ui.HomeScreen
import com.dungnm.example.compose.home.ui.detail.RepoDetailScreen

class HomeRoute : IRoute {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable("home") {
            HomeScreen().Screen(viewModel = hiltViewModel())
        }
        navGraphBuilder.composable(route = "detailRepo") {
            RepoDetailScreen().Screen(viewModel = hiltViewModel())
        }
    }
}