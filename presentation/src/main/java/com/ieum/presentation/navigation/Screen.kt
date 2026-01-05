package com.ieum.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthScreen {
    @Serializable
    data object Login : AuthScreen

    @Serializable
    data object Register : AuthScreen

    @Serializable
    data object Welcome : AuthScreen
}

sealed interface MainScreen {
    @Serializable
    data object Home : MainScreen

    @Serializable
    data class PostWellness(val id: Int?) : MainScreen

    @Serializable
    data class PostDaily(val id: Int?) : MainScreen

    @Serializable
    data class OthersProfile(val id: Int) : MainScreen

    @Serializable
    data object Setting : MainScreen
}