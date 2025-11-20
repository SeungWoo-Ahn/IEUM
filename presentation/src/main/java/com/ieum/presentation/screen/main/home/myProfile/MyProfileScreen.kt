package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.theme.Slate50
import com.ieum.presentation.screen.component.MyProfileTobBar

@Composable
fun MyProfileRoute(
    modifier: Modifier = Modifier,
    moveSetting: () -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    MyProfileScreen(
        modifier = modifier,
        currentTab = viewModel.currentTab,
        onTabClick = viewModel::selectTab,
        moveSetting = moveSetting,
    )
}

@Composable
private fun MyProfileScreen(
    modifier: Modifier,
    currentTab: MyProfileTab,
    onTabClick: (MyProfileTab) -> Unit,
    moveSetting: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate50),
    ) {
        MyProfileTobBar(
            currentTab = currentTab,
            onTabClick = onTabClick,
            onSettingClick = moveSetting,
        )
    }
}