package com.ieum.presentation.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ieum.presentation.screen.component.BottomNavigation
import com.ieum.presentation.screen.component.BottomNavigationItem

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    movePostWellness: () -> Unit,
    movePostDaily: () -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        selectedBottomNavigationItem = BottomNavigationItem.Feed,
        onBottomNavigationItemClick = {},
        movePostWellness = movePostWellness,
        movePostDaily = movePostDaily,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    selectedBottomNavigationItem: BottomNavigationItem,
    onBottomNavigationItemClick: (BottomNavigationItem) -> Unit,
    movePostWellness: () -> Unit,
    movePostDaily: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (selectedBottomNavigationItem) {
            BottomNavigationItem.Feed -> FeedRoute(
                movePostWellness = movePostWellness,
                movePostDaily = movePostDaily,
            )
            BottomNavigationItem.Calendar -> {}
            BottomNavigationItem.Profile -> {}
        }
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedItem = selectedBottomNavigationItem,
            onItemClick = onBottomNavigationItemClick,
        )
    }
}