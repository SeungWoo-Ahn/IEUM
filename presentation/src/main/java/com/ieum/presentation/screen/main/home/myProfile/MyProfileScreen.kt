package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.ieum.design_system.progressbar.IEUMLoadingComponent
import com.ieum.design_system.theme.Slate50
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.PostTypeUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.screen.component.MyProfilePostTypeArea
import com.ieum.presentation.screen.component.MyProfileSection
import com.ieum.presentation.screen.component.MyProfileTobBar
import com.ieum.presentation.screen.component.PostListArea
import com.ieum.presentation.util.GlobalValueModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MyProfileRoute(
    modifier: Modifier = Modifier,
    moveSetting: () -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    MyProfileScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        currentTab = viewModel.currentTab,
        valueModel = viewModel.valueModel,
        postTypeFlow = viewModel.postType,
        postListFlow = viewModel.postListFlow,
        onTabClick = viewModel::onTab,
        onPostType = viewModel::onPostType,
        moveSetting = moveSetting,
        patchDiagnose = {},
        patchChemotherapy = {},
        patchRadiationTherapy = {},
        patchAgeGroup = {},
        patchResidenceArea = {},
        patchHospitalArea = {},
        onMenu = {},
        onLike = {},
        onComment = {},
    )
}

@Composable
private fun MyProfileScreen(
    modifier: Modifier,
    uiState: MyProfileUiState,
    currentTab: MyProfileTab,
    valueModel: GlobalValueModel,
    postTypeFlow: StateFlow<PostTypeUiModel>,
    postListFlow: Flow<PagingData<PostUiModel>>,
    onTabClick: (MyProfileTab) -> Unit,
    onPostType: (PostTypeUiModel) -> Unit,
    moveSetting: () -> Unit,
    patchDiagnose: () -> Unit,
    patchChemotherapy: () -> Unit,
    patchRadiationTherapy: () -> Unit,
    patchAgeGroup: () -> Unit,
    patchResidenceArea: () -> Unit,
    patchHospitalArea: () -> Unit,
    onMenu: (Int) -> Unit,
    onLike: (Int) -> Unit,
    onComment: (Int) -> Unit,
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
        when (currentTab) {
            MyProfileTab.PROFILE -> {
                when (uiState) {
                    MyProfileUiState.Loading -> IEUMLoadingComponent()
                    is MyProfileUiState.Success -> MyProfileSection(
                        profile = uiState.profile.toUiModel(valueModel),
                        patchDiagnose = patchDiagnose,
                        patchChemotherapy = patchChemotherapy,
                        patchRadiationTherapy = patchRadiationTherapy,
                        patchAgeGroup = patchAgeGroup,
                        patchResidenceArea = patchResidenceArea,
                        patchHospitalArea = patchHospitalArea,
                    )
                }
            }
            MyProfileTab.POST_LIST -> {
                val postType by postTypeFlow.collectAsStateWithLifecycle()
                val postList = postListFlow.collectAsLazyPagingItems()
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MyProfilePostTypeArea(
                        currentType = postType,
                        onPostType = onPostType,
                    )
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