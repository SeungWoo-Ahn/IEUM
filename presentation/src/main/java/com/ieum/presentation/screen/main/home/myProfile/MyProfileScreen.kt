package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ieum.presentation.screen.component.CommentListSheet
import com.ieum.presentation.screen.component.ErrorComponent
import com.ieum.presentation.screen.component.MyProfilePostTypeArea
import com.ieum.presentation.screen.component.MyProfileSection
import com.ieum.presentation.screen.component.MyProfileTobBar
import com.ieum.presentation.screen.component.PatchAgeGroupDialog
import com.ieum.presentation.screen.component.PatchChemotherapyDialog
import com.ieum.presentation.screen.component.PatchDiagnoseDialog
import com.ieum.presentation.screen.component.PatchHospitalDialog
import com.ieum.presentation.screen.component.PatchRadiationTherapyDialog
import com.ieum.presentation.screen.component.PatchResidenceDialog
import com.ieum.presentation.screen.component.PatchSurgeryDialog
import com.ieum.presentation.screen.component.PostListArea
import com.ieum.presentation.state.CommentBottomSheetState
import com.ieum.presentation.util.GlobalEvent
import com.ieum.presentation.util.GlobalEventBus
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
    val commentBottomSheetState = viewModel.commentState.bottomSheetState

    MyProfileScreen(
        modifier = modifier,
        currentTab = viewModel.currentTab,
        uiState = viewModel.uiState,
        valueModel = viewModel.valueModel,
        postTypeFlow = viewModel.postType,
        event = viewModel.event,
        postListFlow = viewModel.postListFlow,
        onTabClick = viewModel::onTab,
        onPostType = viewModel::onPostType,
        patchDiagnose = viewModel::showPatchDiagnoseDialog,
        patchSurgery = viewModel::showPatchSurgeryDialog,
        patchChemotherapy = viewModel::showPatchChemotherapyDialog,
        patchRadiationTherapy = viewModel::showPatchRadiationTherapyDialog,
        patchAgeGroup = viewModel::showPatchAgeGroupDialog,
        patchResidenceArea = viewModel::showPatchResidenceDialog,
        patchHospitalArea = viewModel::showPatchHospitalDialog,
        onMenu = {},
        onLike = viewModel::togglePostLike,
        onComment = viewModel::showCommentSheet,
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
    if (dialogState is MyProfileDialogState.ShowPatchSurgeryDialog) {
        PatchSurgeryDialog(
            profile = dialogState.profile,
            patch = dialogState.patch,
            onDismissRequest = viewModel::dismissDialog,
        )
    }
    if (dialogState is MyProfileDialogState.ShowPatchChemotherapyDialog) {
        PatchChemotherapyDialog(
            profile = dialogState.profile,
            patch = dialogState.patch,
            onDismissRequest = viewModel::dismissDialog,
        )
    }
    if (dialogState is MyProfileDialogState.ShowPatchRadiationTherapyDialog) {
        PatchRadiationTherapyDialog(
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
    if (dialogState is MyProfileDialogState.ShowPatchResidenceDialog) {
        PatchResidenceDialog(
            profile = dialogState.profile,
            state = dialogState.state,
            patch = dialogState.patch,
            onDismissRequest = viewModel::dismissDialog,
        )
    }
    if (dialogState is MyProfileDialogState.ShowPatchHospitalDialog) {
        PatchHospitalDialog(
            profile = dialogState.profile,
            state = dialogState.state,
            patch = dialogState.patch,
            onDismissRequest = viewModel::dismissDialog,
        )
    }
    if (commentBottomSheetState is CommentBottomSheetState.Show) {
        CommentListSheet(
            state = commentBottomSheetState,
            onDismissRequest = viewModel.commentState::dismiss
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
    event: Flow<MyProfileEvent>,
    postListFlow: Flow<PagingData<PostUiModel>>,
    onTabClick: (MyProfileTab) -> Unit,
    onPostType: (PostTypeUiModel) -> Unit,
    patchDiagnose: (MyProfile) -> Unit,
    patchSurgery: (MyProfile) -> Unit,
    patchChemotherapy: (MyProfile) -> Unit,
    patchRadiationTherapy: (MyProfile) -> Unit,
    patchAgeGroup: (MyProfile) -> Unit,
    patchResidenceArea: (MyProfile) -> Unit,
    patchHospitalArea: (MyProfile) -> Unit,
    onMenu: (Int) -> Unit,
    onLike: (PostUiModel) -> Unit,
    onComment: (PostUiModel) -> Unit,
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
                        patchSurgery = { patchSurgery(uiState.profile) },
                        patchChemotherapy = { patchChemotherapy(uiState.profile) },
                        patchRadiationTherapy = { patchRadiationTherapy(uiState.profile) },
                        patchAgeGroup = { patchAgeGroup(uiState.profile) },
                        patchResidenceArea = { patchResidenceArea(uiState.profile) },
                        patchHospitalArea = { patchHospitalArea(uiState.profile) },
                    )
                }
            }
            MyProfileTab.POST_LIST -> {
                val postType by postTypeFlow.collectAsStateWithLifecycle()
                val postList = postListFlow.collectAsLazyPagingItems()

                LaunchedEffect(Unit) {
                    GlobalEventBus.eventFlow.collect {
                        when (it) {
                            GlobalEvent.AddMyPost -> postList.refresh()
                        }
                    }
                }

                LaunchedEffect(Unit) {
                    event.collect {
                        when (it) {
                            MyProfileEvent.TogglePostLike -> postList.refresh()
                        }
                    }
                }

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