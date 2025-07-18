package com.ieum.presentation.navigation

sealed interface ScreenGraph {
    data object Auth : ScreenGraph

    data object Main : ScreenGraph
}