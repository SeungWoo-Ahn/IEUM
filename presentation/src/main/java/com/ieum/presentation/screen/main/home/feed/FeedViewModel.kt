package com.ieum.presentation.screen.main.home.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ieum.domain.model.post.Post
import com.ieum.domain.usecase.post.DeletePostUseCase
import com.ieum.domain.usecase.post.GetAllPostListUseCase
import com.ieum.domain.usecase.post.TogglePostLikeUseCase
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.DiagnoseFilterUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.screen.component.DropDownMenu
import com.ieum.presentation.state.CommentState
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
class FeedViewModel @Inject constructor(
    private val getAllPostListUseCase: GetAllPostListUseCase,
    private val togglePostLikeUseCase: TogglePostLikeUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    val commentState: CommentState,
) : ViewModel() {
    var uiState by mutableStateOf<FeedUiState>(FeedUiState.Idle)
        private set

    private val _event = Channel<FeedEvent>()
    val event: Flow<FeedEvent> = _event.receiveAsFlow()

    private val _selectedFilter = MutableStateFlow(DiagnoseFilterUiModel.ENTIRE)
    val selectedFilter: StateFlow<DiagnoseFilterUiModel> get() = _selectedFilter

    val postListFlow: Flow<PagingData<PostUiModel>> =
        selectedFilter
            .flatMapLatest { filter ->
                Pager(
                    config = PagingConfig(pageSize = 5),
                    pagingSourceFactory = { AllPostPagerSource(
                        getAllPostListUseCase = getAllPostListUseCase,
                        diagnosis = filter.toDomain(),
                    ) }
                )
                    .flow
            }
            .map { pagingData ->
                pagingData.map(Post::toUiModel)
            }
            .cachedIn(viewModelScope)

    fun showAddPostDialog() {
        uiState = FeedUiState.ShowAddPostDialog
    }

    fun resetUiState() {
        uiState = FeedUiState.Idle
    }

    fun onFilter(filter: DiagnoseFilterUiModel) {
        _selectedFilter.update { filter }
    }

    fun togglePostLike(post: PostUiModel) {
        viewModelScope.launch {
            togglePostLikeUseCase(id = post.id, type = post.type, isLiked = post.isLiked)
                .onSuccess {
                    _event.send(FeedEvent.TogglePostLike)
                }
        }
    }

    fun showCommentSheet(post: PostUiModel) {
        commentState.showSheet(post, viewModelScope)
    }

    fun onPostNickname(isMine: Boolean, userId: Int) {
        viewModelScope.launch {
            if (isMine) {
                _event.send(FeedEvent.MoveMyProfile)
            } else {
                _event.send(FeedEvent.MoveOthersProfile(userId))
            }
        }
    }

    fun onPostMenu(post: PostUiModel, menu: DropDownMenu) {
        viewModelScope.launch {
            when (menu) {
                DropDownMenu.REPORT -> { }
                DropDownMenu.EDIT -> {
                    _event.send(FeedEvent.MoveEditPost(post.id, post.type))
                }
                DropDownMenu.DELETE -> {
                    deletePostUseCase(post.id, post.type)
                        .onSuccess {
                            _event.send(FeedEvent.DeletePost)
                        }
                        .onFailure {
                            // 삭제 실패
                        }
                }
            }
        }
    }
}