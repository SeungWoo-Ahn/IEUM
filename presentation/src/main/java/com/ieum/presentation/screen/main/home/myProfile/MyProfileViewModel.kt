package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.user.MyProfile
import com.ieum.domain.usecase.address.GetAddressListUseCase
import com.ieum.domain.usecase.post.DeletePostUseCase
import com.ieum.domain.usecase.post.TogglePostLikeUseCase
import com.ieum.domain.usecase.user.GetMyPostListFlowUseCase
import com.ieum.domain.usecase.user.GetMyProfileUseCase
import com.ieum.domain.usecase.user.PatchMyProfileUseCase
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.mapper.toRequest
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.PostTypeUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.screen.component.DropDownMenu
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.CommentState
import com.ieum.presentation.util.ExceptionCollector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getMyPostListFlowUseCase: GetMyPostListFlowUseCase,
    private val patchMyProfileUseCase: PatchMyProfileUseCase,
    private val getAddressListUseCase: GetAddressListUseCase,
    private val togglePostLikeUseCase: TogglePostLikeUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    val commentState: CommentState,
) : ViewModel() {
    var currentTab by mutableStateOf(MyProfileTab.PROFILE)
        private set

    var uiState by mutableStateOf<MyProfileUiState>(MyProfileUiState.Loading)
        private set

    var dialogState by mutableStateOf<MyProfileDialogState>(MyProfileDialogState.Idle)
        private set

    private val _event = Channel<MyProfileEvent>()
    val event: Flow<MyProfileEvent> = _event.receiveAsFlow()

    private val _postType = MutableStateFlow(PostTypeUiModel.WELLNESS)
    val postType: StateFlow<PostTypeUiModel> get() = _postType

    val postListFlow: Flow<PagingData<PostUiModel>> =
        postType
            .flatMapLatest { type ->
                getMyPostListFlowUseCase(type.toDomain())
            }
            .map { pagingData ->
                pagingData.map(Post::toUiModel)
            }
            .cachedIn(viewModelScope)

    init {
        getMyProfile()
    }

    fun onTab(tab: MyProfileTab) {
        currentTab = tab
    }

    fun getMyProfile() {
        uiState = MyProfileUiState.Loading
        viewModelScope.launch {
            getMyProfileUseCase()
                .onSuccess {
                    uiState = MyProfileUiState.Success(it)
                }
                .onFailure { t ->
                    uiState = MyProfileUiState.Error
                    ExceptionCollector.sendException(t)
                }
        }
    }

    fun dismissDialog() {
        dialogState = MyProfileDialogState.Idle
    }

    fun showPatchDiagnoseDialog(profile: MyProfile) {
        dialogState = MyProfileDialogState.ShowPatchDiagnoseDialog(
            profile = profile,
            patch = ::patchMyProfile,
        )
    }

    fun showPatchSurgeryDialog(profile: MyProfile) {
        dialogState = MyProfileDialogState.ShowPatchSurgeryDialog(
            profile = profile,
            patch = ::patchMyProfile,
        )
    }

    fun showPatchChemotherapyDialog(profile: MyProfile) {
        dialogState = MyProfileDialogState.ShowPatchChemotherapyDialog(
            profile = profile,
            patch = ::patchMyProfile,
        )
    }

    fun showPatchRadiationTherapyDialog(profile: MyProfile) {
        dialogState = MyProfileDialogState.ShowPatchRadiationTherapyDialog(
            profile = profile,
            patch = ::patchMyProfile,
        )
    }

    fun showPatchAgeGroupDialog(profile: MyProfile) {
        dialogState = MyProfileDialogState.ShowPatchAgeGroupDialog(
            profile = profile,
            patch = ::patchMyProfile,
        )
    }

    fun showPatchResidenceDialog(profile: MyProfile) {
        dialogState = MyProfileDialogState.ShowPatchResidenceDialog(
            profile = profile,
            state = AddressState(
                getAddressListUseCase = getAddressListUseCase,
                coroutineScope = viewModelScope,
            ),
            patch = ::patchMyProfile,
        )
    }

    fun showPatchHospitalDialog(profile: MyProfile) {
        dialogState = MyProfileDialogState.ShowPatchHospitalDialog(
            profile = profile,
            state = AddressState(
                getAddressListUseCase = getAddressListUseCase,
                coroutineScope = viewModelScope,
            ),
            patch = ::patchMyProfile,
        )
    }

    private fun patchMyProfile(profile: MyProfile, onFailure: () -> Unit) {
        viewModelScope.launch {
            patchMyProfileUseCase(profile.toRequest())
                .onSuccess {
                    uiState = MyProfileUiState.Success(it)
                    dismissDialog()
                }
                .onFailure { t ->
                    onFailure()
                    ExceptionCollector.sendException(t)
                }
        }
    }

    fun onPostType(type: PostTypeUiModel) {
        _postType.update { type }
    }

    fun togglePostLike(post: PostUiModel) {
        viewModelScope.launch {
            togglePostLikeUseCase(
                id = post.id,
                type = post.type,
                isLiked = post.isLiked
            )
        }
    }

    fun showCommentSheet(post: PostUiModel) {
        commentState.showSheet(post, viewModelScope)
    }

    fun onPostMenu(post: PostUiModel, menu: DropDownMenu) {
        viewModelScope.launch {
            when (menu) {
                DropDownMenu.REPORT -> {/*not-used*/}
                DropDownMenu.EDIT -> {
                    _event.send(MyProfileEvent.MoveEditPost(post.id, post.type))
                }
                DropDownMenu.DELETE -> {
                    deletePostUseCase(post.id, post.type)
                        .onFailure { t ->
                            ExceptionCollector.sendException(t)
                        }
                }
            }
        }
    }
}