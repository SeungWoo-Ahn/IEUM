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
import com.ieum.presentation.screen.component.OthersProfileSection
import com.ieum.presentation.screen.component.OthersProfileTabArea
import com.ieum.presentation.screen.component.PostListArea
import kotlinx.coroutines.flow.Flow

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
        event = viewModel.event,
        postListFlow = viewModel.postListFlow,
        onTabClick = viewModel::onTab,
        onMenu = {},
        onLike = {},
        onComment = {},
        onBack = onBack,
    )
}

@Composable
private fun OthersProfileScreen(
    modifier: Modifier,
    uiState: OthersProfileUiState,
    currentTab: OthersProfileTab,
    event: Flow<OtherProfileEvent>,
    postListFlow: Flow<PagingData<PostUiModel>>,
    onTabClick: (OthersProfileTab) -> Unit,
    onMenu: (Int) -> Unit,
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

                        LaunchedEffect(Unit) {
                            event.collect {
                                when (it) {
                                    OtherProfileEvent.TogglePostLike -> postList.refresh()
                                }
                            }
                        }

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