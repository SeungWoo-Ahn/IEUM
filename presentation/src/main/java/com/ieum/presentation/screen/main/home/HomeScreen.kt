package com.ieum.presentation.screen.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    movePostWellness: () -> Unit,
    movePostDaily: () -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        movePostWellness = movePostWellness,
        movePostDaily = movePostDaily,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    movePostWellness: () -> Unit,
    movePostDaily: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        FeedRoute(
            movePostWellness = movePostWellness,
            movePostDaily = movePostDaily,
        )
       // TODO: BottomBar
    }
}