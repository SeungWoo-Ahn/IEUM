package com.ieum.presentation.screen.main.othersProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.progressbar.IEUMLoadingComponent
import com.ieum.design_system.theme.Slate50
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.presentation.screen.component.OthersProfileSection
import com.ieum.presentation.screen.component.OthersProfileTabArea

@Composable
fun OthersProfileRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: OthersProfileViewModel = hiltViewModel(),
) {
    OthersProfileScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        currentTab = viewModel.currentTab,
        onTabClick = viewModel::selectTab,
        onBack = onBack,
    )
}

@Composable
private fun OthersProfileScreen(
    modifier: Modifier,
    currentTab: OthersProfileTab,
    uiState: OthersProfileUiState,
    onTabClick: (OthersProfileTab) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate50),
    ) {
        TopBarForBack(onBack = onBack)
        when (uiState) {
            OthersProfileUiState.Loading -> IEUMLoadingComponent()
            is OthersProfileUiState.Success -> {
                OthersProfileTabArea(currentTab = currentTab, onTabClick = onTabClick)
                when (currentTab) {
                    OthersProfileTab.PROFILE -> OthersProfileSection(profile = uiState.profile)
                    OthersProfileTab.POST_LIST -> {}
                }
            }
        }
    }
}