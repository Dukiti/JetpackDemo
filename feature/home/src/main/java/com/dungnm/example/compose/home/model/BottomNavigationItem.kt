package com.dungnm.example.compose.home.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String, val icon: ImageVector, val route: String
) {

    companion object {
        fun bottomNavigationItems(): List<BottomNavigationItem> {
            return listOf(
                BottomNavigationItem(
                    label = "Home", icon = Icons.Filled.Home, route = "home/main"
                ),
//            BottomNavigationItem(
//                label = "Search", icon = Icons.Filled.Search, route = "home/search"
//            ),
                BottomNavigationItem(
                    label = "Setting", icon = Icons.Filled.Settings, route = "setting"
                ),
            )
        }
    }
}