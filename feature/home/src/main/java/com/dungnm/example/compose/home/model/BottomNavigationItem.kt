package com.dungnm.example.compose.home.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String = "", val icon: ImageVector = Icons.Filled.Home, val route: String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Home", icon = Icons.Filled.Home, route = "main"
            ),
            BottomNavigationItem(
                label = "Search", icon = Icons.Filled.Search, route = "main"
            ),
            BottomNavigationItem(
                label = "Setting", icon = Icons.Filled.Settings, route = "setting"
            ),
        )
    }
}