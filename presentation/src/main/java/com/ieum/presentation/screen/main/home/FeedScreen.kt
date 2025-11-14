package com.ieum.presentation.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.FeedTopBar
import com.ieum.presentation.model.post.DiagnoseFilterUiModel
import com.ieum.presentation.screen.component.AddPostDialog
import com.ieum.presentation.screen.component.DiagnoseFilterArea
import com.ieum.presentation.screen.component.WriteFAB

@Composable
fun FeedRoute(
    modifier: Modifier = Modifier,
    movePostWellness: () -> Unit,
    movePostDaily: () -> Unit,
    viewModel: FeedViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    FeedScreen(
        modifier = modifier,
        selectedFilter = selectedFilter,
        onFilter = viewModel::onFilter,
        showAddPostDialog = viewModel::showAddPostDialog
    )
    if (uiState is FeedUiState.ShowAddPostDialog) {
        AddPostDialog(
            movePostWellness = movePostWellness,
            movePostDaily = movePostDaily,
            onDismissRequest = viewModel::resetUiState,
        )
    }
}

@Composable
private fun FeedScreen(
    modifier: Modifier,
    selectedFilter: DiagnoseFilterUiModel,
    onFilter: (DiagnoseFilterUiModel) -> Unit,
    showAddPostDialog: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            FeedTopBar()
            DiagnoseFilterArea(
                selectedFilter = selectedFilter,
                onFilter = onFilter,
            )
        }
        WriteFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = screenPadding),
            onClick = showAddPostDialog,
        )
    }
}
