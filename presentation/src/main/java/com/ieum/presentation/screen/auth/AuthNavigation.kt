package com.ieum.presentation.screen.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ieum.presentation.navigation.AuthScreen
import com.ieum.presentation.navigation.ScreenGraph
import com.ieum.presentation.screen.IEUMAppState

fun NavGraphBuilder.nestedAuthGraph(appState: IEUMAppState) {
    val navController = appState.navController

    navigation<ScreenGraph.Auth>(startDestination = AuthScreen.Login) {
        composable<AuthScreen.Login> {

        }
        composable<AuthScreen.Register> {

        }
        composable<AuthScreen.Welcome> {

        }
    }
}

fun NavController.navigateToAuthGraph() = navigate(ScreenGraph.Auth) {
    popUpTo(graph.id) { inclusive = true }
}

fun NavController.navigateToRegisterScreen() = navigate(AuthScreen.Register)

fun NavController.navigateToWelcomeScreen() = navigate(AuthScreen.Welcome)