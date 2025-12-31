package com.ieum.presentation.screen.main.othersProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ieum.domain.model.post.Post
import com.ieum.domain.usecase.post.TogglePostLikeUseCase
import com.ieum.domain.usecase.user.GetOthersPostListUseCase
import com.ieum.domain.usecase.user.GetOthersProfileUseCase
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.state.CommentState
import com.ieum.presentation.util.GlobalValueModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OthersProfileViewModel @Inject constructor(
    private val getOthersProfileUseCase: GetOthersProfileUseCase,
    private val getOthersPostListUseCase: GetOthersPostListUseCase,
    private val togglePostLikeUseCase: TogglePostLikeUseCase,
    private val valueModel: GlobalValueModel,
    val commentState: CommentState,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<MainScreen.OthersProfile>().id

    var uiState by mutableStateOf<OthersProfileUiState>(OthersProfileUiState.Loading)
        private set

    var currentTab by mutableStateOf(OthersProfileTab.PROFILE)
        private set

    private val _event = Channel<OtherProfileEvent>()
    val event: Flow<OtherProfileEvent> = _event.receiveAsFlow()

    val postListFlow: Flow<PagingData<PostUiModel>> =
        Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { OthersPostPagerSource(
                getOthersPostListUseCase = getOthersPostListUseCase,
                id = id,
            ) }
        )
            .flow
            .map { pagingData ->
                pagingData.map(Post::toUiModel)
            }
            .cachedIn(viewModelScope)


    init {
        getOthersProfile()
    }

    private fun getOthersProfile() {
        viewModelScope.launch {
            getOthersProfileUseCase(id)
                .onSuccess { profile ->
                    uiState = OthersProfileUiState.Success(
                        profile = profile.toUiModel(valueModel)
                    )
                }
                .onFailure {
                    // 불러오기 실패
                }
        }
    }

    fun onTab(tab: OthersProfileTab) {
        currentTab = tab
    }

    fun togglePostLike(post: PostUiModel) {
        viewModelScope.launch {
            togglePostLikeUseCase(id = post.id, type = post.type, isLiked = post.isLiked)
                .onSuccess {
                    _event.send(OtherProfileEvent.TogglePostLike)
                }
        }
    }


    fun showCommentSheet(post: PostUiModel) {
        commentState.showSheet(post, viewModelScope)
    }
}