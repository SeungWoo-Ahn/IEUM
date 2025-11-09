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
    data class PostWellness(val id: String?) : MainScreen

    @Serializable
    data class PostDaily(val id: String?) : MainScreen
}