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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.FeedTopBar
import com.ieum.presentation.model.post.DiagnoseFilterUiModel
import com.ieum.presentation.model.post.PostUiModel
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
    val postList = viewModel.postList.collectAsLazyPagingItems()

    FeedScreen(
        modifier = modifier,
        selectedFilter = selectedFilter,
        postList = postList,
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
    postList: LazyPagingItems<PostUiModel>,
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
            when (postList.loadState.refresh) {
                LoadState.Loading -> TODO()
                is LoadState.Error -> TODO()
                is LoadState.NotLoading -> TODO()
            }
        }
        WriteFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = screenPadding),
            onClick = showAddPostDialog,
        )
    }
}
