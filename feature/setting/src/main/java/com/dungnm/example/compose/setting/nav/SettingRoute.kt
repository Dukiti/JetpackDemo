package com.dungnm.example.compose.setting.nav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dungnm.example.compose.core.navigation.IRoute
import com.dungnm.example.compose.setting.ui.SettingScreen

class SettingRoute : IRoute {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable("setting") {
            SettingScreen().Screen(viewModel = hiltViewModel())
        }
    }
}