package com.ieum.presentation.screen.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.navigation.ScreenGraph
import com.ieum.presentation.screen.IEUMAppState
import com.ieum.presentation.screen.main.home.HomeRoute
import com.ieum.presentation.screen.main.othersProfile.OthersProfileRoute
import com.ieum.presentation.screen.main.postDaily.PostDailyRoute
import com.ieum.presentation.screen.main.postWellness.PostWellnessRoute

fun NavGraphBuilder.nestedMainGraph(appState: IEUMAppState) {
    val navController = appState.navController

    navigation<ScreenGraph.Main>(startDestination = MainScreen.Home) {
        composable<MainScreen.Home> {
            HomeRoute(
                scope = appState.coroutineScope,
                movePostWellness = navController::navigateToPostWellnessScreen,
                movePostDaily = navController::navigateToPostDailyScreen,
                moveSetting = {},
                moveOthersProfile = navController::navigateToOthersProfile
            )
        }
        composable<MainScreen.PostWellness> {
            PostWellnessRoute(
                scope = appState.coroutineScope,
                onBack = navController::popBackStack,
            )
        }
        composable<MainScreen.PostDaily> {
            PostDailyRoute(
                onBack = navController::popBackStack,
            )
        }
        composable<MainScreen.OthersProfile> {
            OthersProfileRoute(
                onBack = navController::popBackStack,
            )
        }
    }
}

fun NavController.navigateToMainGraph() = navigate(ScreenGraph.Main) {
    popUpTo(graph.id) { inclusive = true }
}

fun NavController.navigateToPostWellnessScreen(id: Int? = null) =
    navigate(MainScreen.PostWellness(id))

fun NavController.navigateToPostDailyScreen(id: Int? = null) =
    navigate(MainScreen.PostDaily(id))

fun NavController.navigateToOthersProfile(id: Int) =
    navigate(MainScreen.OthersProfile(id))