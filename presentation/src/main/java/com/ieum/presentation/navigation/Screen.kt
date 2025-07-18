package com.ieum.presentation.navigation

sealed interface LoginScreen {
    data object Login : LoginScreen

    data object Register : LoginScreen
}

sealed interface MainScreen {
    data object Home : MainScreen
}