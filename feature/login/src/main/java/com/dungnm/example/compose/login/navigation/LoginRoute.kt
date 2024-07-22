package com.dungnm.example.compose.login.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dungnm.example.compose.core.navigation.IRoute
import com.dungnm.example.compose.login.ui.LoginScreen

class LoginRoute : IRoute {

    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable("login") {
            LoginScreen().Screen(viewModel = hiltViewModel())
        }
    }
}