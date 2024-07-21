package com.dungnm.example.compose.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dungnm.example.compose.core.feature_api.FeatureApi
import com.dungnm.example.compose.home.ui.HomeScreen
import com.dungnm.example.compose.home.ui.detail.RepoDetailScreen

class HomeFeatureApi : FeatureApi {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable("home") {
            HomeScreen().Screen(viewModel = hiltViewModel())
        }
        navGraphBuilder.composable(route = "detailRepo") {
            RepoDetailScreen().Screen(viewModel = hiltViewModel())
        }
    }
}