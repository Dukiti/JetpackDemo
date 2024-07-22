package com.dungnm.example.compose.core.navigation

import androidx.navigation.NavGraphBuilder

interface IRoute {

    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
    )
}