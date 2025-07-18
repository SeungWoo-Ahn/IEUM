package com.ieum.presentation.navigation

sealed interface AuthScreen {
    data object Login : AuthScreen

    data object Register : AuthScreen

    data object Welcome : AuthScreen
}

sealed interface MainScreen {
    data object Home : MainScreen
}