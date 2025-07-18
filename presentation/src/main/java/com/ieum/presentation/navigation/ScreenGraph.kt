package com.ieum.presentation.navigation

sealed interface ScreenGraph {
    data object Login : ScreenGraph

    data object Main : ScreenGraph
}