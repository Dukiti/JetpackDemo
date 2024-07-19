package com.dungnm.example.compose.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
//        register(
//            DependencyProvider.homeFeature(), navController = navController, modifier = modifier
//        )
//
//        register(
//            DependencyProvider.settingsFeature(), navController = navController, modifier = modifier
//        )
//
//        register(
//            DependencyProvider.onboardingFeature(),
//            navController = navController,
//            modifier = modifier
//        )
    }
}
