package com.ieum.presentation.screen.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.navigation.ScreenGraph
import com.ieum.presentation.screen.IEUMAppState
import com.ieum.presentation.screen.main.home.HomeRoute
import com.ieum.presentation.screen.main.postDailyRecords.PostDailyRecordsRoute
import com.ieum.presentation.screen.main.postTreatmentRecords.PostTreatmentRecordsRoute

fun NavGraphBuilder.nestedMainGraph(appState: IEUMAppState) {
    val navController = appState.navController

    navigation<ScreenGraph.Main>(startDestination = MainScreen.Home) {
        composable<MainScreen.Home> {
            HomeRoute(
                movePostTreatmentRecords = navController::navigateToPostTreatmentRecordsScreen,
                movePostDailyRecords = navController::navigateToPostDailyRecordsScreen,
            )
        }
        composable<MainScreen.PostTreatmentRecords> {
            PostTreatmentRecordsRoute(
                scope = appState.coroutineScope,
                onBack = navController::popBackStack,
            )
        }
        composable<MainScreen.PostDailyRecords> {
            PostDailyRecordsRoute(
                onBack = navController::popBackStack,
            )
        }
    }
}

fun NavController.navigateToMainGraph() = navigate(ScreenGraph.Main) {
    popUpTo(graph.id) { inclusive = true }
}

fun NavController.navigateToPostTreatmentRecordsScreen(id: String? = null) =
    navigate(MainScreen.PostTreatmentRecords(id))

fun NavController.navigateToPostDailyRecordsScreen(id: String? = null) =
    navigate(MainScreen.PostDailyRecords(id))