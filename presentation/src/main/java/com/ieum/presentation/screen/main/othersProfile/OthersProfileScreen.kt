package com.ieum.presentation.screen.main.othersProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.ieum.design_system.progressbar.IEUMLoadingComponent
import com.ieum.design_system.theme.Slate50
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.screen.component.CommentListSheet
import com.ieum.presentation.screen.component.DropDownMenu
import com.ieum.presentation.screen.component.OthersProfileSection
import com.ieum.presentation.screen.component.OthersProfileTabArea
import com.ieum.presentation.screen.component.PostListArea
import com.ieum.presentation.screen.component.ReportSheet
import com.ieum.presentation.state.CommentBottomSheetState
import com.ieum.presentation.state.ReportPostBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Composable
fun OthersProfileRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    onBack: () -> Unit,
    viewModel: OthersProfileViewModel = hiltViewModel(),
) {
    val commentBottomSheetState = viewModel.commentState.bottomSheetState
    val reportPostBottomSheetState = viewModel.reportPostState.bottomSheetState

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                OtherProfileEvent.MoveBack -> onBack()
            }
        }
    }

    OthersProfileScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        currentTab = viewModel.currentTab,
        postListFlow = viewModel.postListFlow,
        onTabClick = viewModel::onTab,
        onMenu = viewModel::onPostMenu,
        onLike = viewModel::togglePostLike,
        onComment = viewModel::showCommentSheet,
        onBack = onBack,
    )

    if (commentBottomSheetState is CommentBottomSheetState.Show) {
        CommentListSheet(
            state = commentBottomSheetState,
            onDismissRequest = viewModel.commentState::dismiss
        )
    }
    if (reportPostBottomSheetState is ReportPostBottomSheetState.Show) {
        ReportSheet(
            scope = scope,
            reportEnabled = reportPostBottomSheetState.isLoading.not(),
            onReport = reportPostBottomSheetState::onReport,
            onDismissRequest = viewModel.reportPostState::dismiss
        )
    }
}

@Composable
private fun OthersProfileScreen(
    modifier: Modifier,
    uiState: OthersProfileUiState,
    currentTab: OthersProfileTab,
    postListFlow: Flow<PagingData<PostUiModel>>,
    onTabClick: (OthersProfileTab) -> Unit,
    onMenu: (PostUiModel, DropDownMenu) -> Unit,
    onLike: (PostUiModel) -> Unit,
    onComment: (PostUiModel) -> Unit,
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
                    OthersProfileTab.POST_LIST -> {
                        val postList = postListFlow.collectAsLazyPagingItems()

                        PostListArea(
                            postList = postList,
                            onMenu = onMenu,
                            onLike = onLike,
                            onComment = onComment,
                        )
                    }
                }
            }
        }
    }
}