package com.ieum.presentation.screen.main.setting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingRoute(
    modifier: Modifier = Modifier,
    moveBack: () -> Unit,
) {
    SettingScreen(
        modifier = modifier,
        onLogout = {},
        moveBack = {},
    )
}

@Composable
private fun SettingScreen(
    modifier: Modifier,
    onLogout: () -> Unit,
    moveBack: () -> Unit,
) {

}