package com.ieum.presentation.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.presentation.screen.component.BottomNavigation
import com.ieum.presentation.screen.component.BottomNavigationItem
import com.ieum.presentation.screen.main.home.feed.FeedRoute
import com.ieum.presentation.screen.main.home.myProfile.MyProfileRoute
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    movePostWellness: (Int?) -> Unit,
    movePostDaily: (Int?) -> Unit,
    moveOthersProfile: (Int) -> Unit,
    moveSetting: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        modifier = modifier,
        scope = scope,
        selectedBottomNavigationItem = viewModel.selectedBottomNavigationItem,
        onBottomNavigationItemClick = viewModel::onBottomNavigationItemClick,
        movePostWellness = movePostWellness,
        movePostDaily = movePostDaily,
        moveOthersProfile = moveOthersProfile,
        moveSetting = moveSetting,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    scope: CoroutineScope,
    selectedBottomNavigationItem: BottomNavigationItem,
    onBottomNavigationItemClick: (BottomNavigationItem) -> Unit,
    movePostWellness: (Int?) -> Unit,
    movePostDaily: (Int?) -> Unit,
    moveOthersProfile: (Int) -> Unit,
    moveSetting: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (selectedBottomNavigationItem) {
            BottomNavigationItem.Feed -> FeedRoute(
                movePostWellness = movePostWellness,
                movePostDaily = movePostDaily,
                moveOthersProfile = moveOthersProfile,
                moveMyProfile = { onBottomNavigationItemClick(BottomNavigationItem.Profile) }
            )
            BottomNavigationItem.Calendar -> {}
            BottomNavigationItem.Profile -> MyProfileRoute(
                scope = scope,
                moveEditWellness = movePostWellness,
                moveEditDaily = movePostDaily,
                moveSetting = moveSetting,
            )
        }
        BottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter),
            selectedItem = selectedBottomNavigationItem,
            onItemClick = onBottomNavigationItemClick,
        )
    }
}