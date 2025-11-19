package com.ieum.presentation.screen.main.home.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.FeedTopBar
import com.ieum.presentation.model.post.DiagnoseFilterUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.screen.component.AddPostDialog
import com.ieum.presentation.screen.component.DiagnoseFilterArea
import com.ieum.presentation.screen.component.PostListArea
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
        onNickname = {},
        onMenu = {},
        onLike = {},
        onComment = {},
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
    onNickname: (Int) -> Unit,
    onMenu: (Int) -> Unit,
    onLike: (Int) -> Unit,
    onComment: (Int) -> Unit,
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
            PostListArea(
                postList = postList,
                onNickname = onNickname,
                onMenu = onMenu,
                onLike = onLike,
                onComment = onComment,
            )
        }
        WriteFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = screenPadding,
                    bottom = 90.dp,
                ),
            onClick = showAddPostDialog,
        )
    }
}
