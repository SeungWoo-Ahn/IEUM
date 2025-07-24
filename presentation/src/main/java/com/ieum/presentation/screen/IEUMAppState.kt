package com.ieum.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Stable
data class IEUMAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
)

@Composable
fun rememberIEUMAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): IEUMAppState =
    remember(navController, coroutineScope) {
        IEUMAppState(navController, coroutineScope)
    }