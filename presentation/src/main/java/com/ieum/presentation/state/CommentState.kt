package com.ieum.presentation.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.map
import com.ieum.design_system.textfield.MaxLengthTextFieldState
import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.PostCommentRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.usecase.post.DeleteCommentUseCase
import com.ieum.domain.usecase.post.GetCommentListUseCase
import com.ieum.domain.usecase.post.PostCommentUseCase
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.CommentUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.screen.component.DropDownMenu
import com.ieum.presentation.util.ExceptionCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CommentBottomSheetState {
    data object Idle : CommentBottomSheetState()

    data class Show(
        private val scope: CoroutineScope,
        private val postId: Int,
        private val type: PostType,
        private val getCommentListUseCase: GetCommentListUseCase,
        private val postCommentUseCase: PostCommentUseCase,
        private val deleteCommentUseCase: DeleteCommentUseCase,
    ) : CommentBottomSheetState() {
        private val _event = Channel<CommentBottomSheetEvent>()
        val event: Flow<CommentBottomSheetEvent> = _event.receiveAsFlow()

        var isLoading by mutableStateOf(false)
            private set

        val typedCommentState = MaxLengthTextFieldState(maxLength = 500)

        val commentList: Flow<PagingData<CommentUiModel>> =
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { CommentPagerSource(
                    getCommentListUseCase = getCommentListUseCase,
                    postId = postId,
                    type = type,
                ) }
            )
                .flow
                .map { pagingData ->
                    pagingData.map(Comment::toUiModel)
                }
                .cachedIn(scope)

        fun postComment() {
            scope.launch {
                val request = PostCommentRequest(
                    postId = postId,
                    postType = type,
                    content = typedCommentState.getTrimmedText()
                )
                isLoading = true
                postCommentUseCase(request)
                    .onSuccess {
                        _event.send(CommentBottomSheetEvent.RefreshCommentList)
                        typedCommentState.resetText()
                    }
                    .onFailure { t ->
                        ExceptionCollector.sendException(t)
                    }
                isLoading = false
            }
        }

        fun onMenu(commendId: Int, selectedMenu: DropDownMenu) {
            when (selectedMenu) {
                DropDownMenu.REPORT -> {}
                DropDownMenu.EDIT -> {}
                DropDownMenu.DELETE -> deleteComment(commendId)
            }
        }

        private fun deleteComment(commentId: Int) {
            scope.launch {
                isLoading = true
                deleteCommentUseCase(
                    postId = postId,
                    type = type,
                    commentId = commentId
                ).onSuccess {
                    _event.send(CommentBottomSheetEvent.RefreshCommentList)
                }.onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
                isLoading = false
            }
        }
    }
}

sealed class CommentBottomSheetEvent {
    data object RefreshCommentList : CommentBottomSheetEvent()
}

class CommentState @Inject constructor(
    private val getCommentListUseCase: GetCommentListUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
) {
    var bottomSheetState by mutableStateOf<CommentBottomSheetState>(CommentBottomSheetState.Idle)
        private set

    fun dismiss() {
        bottomSheetState = CommentBottomSheetState.Idle
    }

    fun showSheet(post: PostUiModel, scope: CoroutineScope) {
        bottomSheetState = CommentBottomSheetState.Show(
            scope = scope,
            postId = post.id,
            type = post.type,
            getCommentListUseCase = getCommentListUseCase,
            postCommentUseCase = postCommentUseCase,
            deleteCommentUseCase = deleteCommentUseCase,
        )
    }
}

private class CommentPagerSource(
    private val getCommentListUseCase: GetCommentListUseCase,
    private val postId: Int,
    private val type: PostType,
) : PagingSource<Int, Comment>() {
    override fun getRefreshKey(state: PagingState<Int, Comment>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        val page = params.key ?: 1
        return try {
            val response = getCommentListUseCase(
                page = page,
                size = 20,
                postId = postId,
                type = type,
            ).getOrThrow()
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.size < params.loadSize) null else page + 1
            LoadResult.Page(response, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}