package com.dungnm.example.compose.core.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.dungnm.example.compose.core.feature_api.FeatureApi
import dagger.hilt.android.EntryPointAccessors

fun NavGraphBuilder.register(
    featureApi: FeatureApi,
) {
    featureApi.registerGraph(navGraphBuilder = this,)
}

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    graph.findNode(route)?.id?.let { nodeId ->
        navigate(
            resId = nodeId, args = args, navOptions = navOptions, navigatorExtras = navigatorExtras
        )
    } ?: error("route $route not found")
}

@Composable
fun rememberMainNav(
    vararg navigators: Navigator<out NavDestination>
): NavHostController {
    val context = LocalContext.current
    return rememberSaveable {
        EntryPointAccessors.fromApplication<NavEntryPoint>(context).mainNav()
    }
}