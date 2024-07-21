package com.dungnm.example.compose.login.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dungnm.example.compose.core.feature_api.FeatureApi
import com.dungnm.example.compose.login.ui.LoginScreen

class LoginFeatureApi : FeatureApi {

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable("login") {
            LoginScreen().Screen(viewModel = hiltViewModel())
        }
    }
}