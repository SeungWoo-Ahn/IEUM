package com.ieum.presentation.screen.main.othersProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.theme.Slate50
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.presentation.screen.component.OthersProfileTabArea

@Composable
fun OthersProfileRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: OthersProfileViewModel = hiltViewModel(),
) {
    OthersProfileScreen(
        modifier = modifier,
        currentTab = viewModel.currentTab,
        onTabClick = viewModel::selectTab,
        onBack = onBack,
    )
}

@Composable
private fun OthersProfileScreen(
    modifier: Modifier,
    currentTab: OthersProfileTab,
    onTabClick: (OthersProfileTab) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate50),
    ) {
        TopBarForBack(onBack = onBack)
        OthersProfileTabArea(
            currentTab = currentTab,
            onTabClick = onTabClick,
        )
        when (currentTab) {
            OthersProfileTab.PROFILE -> {}
            OthersProfileTab.POST_LIST -> {}
        }
    }
}