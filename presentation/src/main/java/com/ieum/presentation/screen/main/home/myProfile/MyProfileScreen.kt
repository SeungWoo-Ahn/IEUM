package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MyProfileRoute(
    modifier: Modifier = Modifier,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    MyProfileScreen(
        modifier = modifier,
    )
}

@Composable
private fun MyProfileScreen(
    modifier: Modifier,
) {

}