package com.dungnm.example.compose.home.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dungnm.example.compose.core.base.BaseScreen
import com.dungnm.example.compose.home.model.BottomNavigationItem
import com.dungnm.example.compose.home.ui.search.SearchScreen

class HomeScreen : BaseScreen<HomeViewModel>() {

    @Composable
    override fun Screen(viewModel: HomeViewModel) {
        super.Screen(viewModel)
    }

    @Composable
    override fun ContentView(viewModel: HomeViewModel, innerPadding: PaddingValues) {
        var navigationSelectedItem by remember {
            mutableStateOf(0)
        }
        val navController = rememberNavController()
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(selected = index == navigationSelectedItem, label = {
                            Text(navigationItem.label)
                        }, icon = {
                            Icon(
                                navigationItem.icon, contentDescription = navigationItem.label
                            )
                        }, onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                    }
            }
        }) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "main",
//                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
                composable("main") {
                    SearchScreen().ContentView()
                }
                composable("search") {
//                    SearchScreen().ContentView()
                }
                composable("setting") {
                    //call our composable screens here
                }
            }
        }
    }


//    @Preview(showBackground = true, device = Devices.PIXEL_2)
//    @Composable
//    fun Preview() {
//        MainAppTheme(content = {
//            Screen(HomeViewModel())
//        })
//    }
}