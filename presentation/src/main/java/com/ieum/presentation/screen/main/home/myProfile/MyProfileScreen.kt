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
import com.ieum.domain.model.user.MyProfile
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.PostTypeUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.screen.component.ErrorComponent
import com.ieum.presentation.screen.component.MyProfilePostTypeArea
import com.ieum.presentation.screen.component.MyProfileSection
import com.ieum.presentation.screen.component.MyProfileTobBar
import com.ieum.presentation.screen.component.PatchAgeGroupDialog
import com.ieum.presentation.screen.component.PatchDiagnoseDialog
import com.ieum.presentation.screen.component.PostListArea
import com.ieum.presentation.util.GlobalValueModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MyProfileRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    moveSetting: () -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    val dialogState = viewModel.dialogState

    MyProfileScreen(
        modifier = modifier,
        currentTab = viewModel.currentTab,
        uiState = viewModel.uiState,
        valueModel = viewModel.valueModel,
        postTypeFlow = viewModel.postType,
        postListFlow = viewModel.postListFlow,
        onTabClick = viewModel::onTab,
        onPostType = viewModel::onPostType,
        patchDiagnose = viewModel::showPatchDiagnoseDialog,
        patchChemotherapy = {},
        patchRadiationTherapy = {},
        patchAgeGroup = viewModel::showPatchAgeGroupDialog,
        patchResidenceArea = {},
        patchHospitalArea = {},
        onMenu = {},
        onLike = {},
        onComment = {},
        moveSetting = moveSetting,
        getMyProfile = viewModel::getMyProfile,
    )
    if (dialogState is MyProfileDialogState.ShowPatchDiagnoseDialog) {
        PatchDiagnoseDialog(
            scope = scope,
            profile = dialogState.profile,
            patch = dialogState.patch,
            onDismissRequest = viewModel::dismissDialog,
        )
    }
    if (dialogState is MyProfileDialogState.ShowPatchAgeGroupDialog) {
        PatchAgeGroupDialog(
            profile = dialogState.profile,
            patch = dialogState.patch,
            onDismissRequest = viewModel::dismissDialog,
        )
    }
}

@Composable
private fun MyProfileScreen(
    modifier: Modifier,
    currentTab: MyProfileTab,
    uiState: MyProfileUiState,
    valueModel: GlobalValueModel,
    postTypeFlow: StateFlow<PostTypeUiModel>,
    postListFlow: Flow<PagingData<PostUiModel>>,
    onTabClick: (MyProfileTab) -> Unit,
    onPostType: (PostTypeUiModel) -> Unit,
    patchDiagnose: (MyProfile) -> Unit,
    patchChemotherapy: () -> Unit,
    patchRadiationTherapy: () -> Unit,
    patchAgeGroup: (MyProfile) -> Unit,
    patchResidenceArea: () -> Unit,
    patchHospitalArea: () -> Unit,
    onMenu: (Int) -> Unit,
    onLike: (Int) -> Unit,
    onComment: (Int) -> Unit,
    moveSetting: () -> Unit,
    getMyProfile: () -> Unit,
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
                    MyProfileUiState.Error -> ErrorComponent(onRetry = getMyProfile)
                    is MyProfileUiState.Success -> MyProfileSection(
                        profile = uiState.profile.toUiModel(valueModel),
                        patchDiagnose = { patchDiagnose(uiState.profile) },
                        patchChemotherapy = patchChemotherapy,
                        patchRadiationTherapy = patchRadiationTherapy,
                        patchAgeGroup = { patchAgeGroup(uiState.profile) },
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