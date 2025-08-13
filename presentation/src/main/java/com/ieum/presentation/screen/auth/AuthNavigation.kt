package com.ieum.presentation.screen.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ieum.presentation.navigation.AuthScreen
import com.ieum.presentation.navigation.ScreenGraph
import com.ieum.presentation.screen.IEUMAppState
import com.ieum.presentation.screen.auth.login.LoginRoute
import com.ieum.presentation.screen.auth.register.RegisterRoute
import com.ieum.presentation.screen.auth.welcome.WelcomeRoute
import com.ieum.presentation.screen.main.navigateToMainGraph

fun NavGraphBuilder.nestedAuthGraph(appState: IEUMAppState) {
    val navController = appState.navController

    navigation<ScreenGraph.Auth>(startDestination = AuthScreen.Login) {
        composable<AuthScreen.Login> {
            LoginRoute(
                moveMain = navController::navigateToMainGraph,
                moveRegister = navController::navigateToRegisterScreen,
            )
        }
        composable<AuthScreen.Register> {
            RegisterRoute(
                scope = appState.coroutineScope,
                moveWelcome = navController::navigateToWelcomeScreen,
                onBack = navController::popBackStack,
            )
        }
        composable<AuthScreen.Welcome> {
            WelcomeRoute(
                moveMain = navController::navigateToMainGraph,
            )
        }
    }
}

fun NavController.navigateToAuthGraph() = navigate(ScreenGraph.Auth) {
    popUpTo(graph.id) { inclusive = true }
}

fun NavController.navigateToRegisterScreen() = navigate(AuthScreen.Register)

fun NavController.navigateToWelcomeScreen() = navigate(AuthScreen.Welcome)