package com.ieum.presentation.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ieum.design_system.textfield.MaxLengthTextFieldState
import com.ieum.domain.model.post.Comment
import com.ieum.domain.model.post.PostCommentRequest
import com.ieum.domain.model.post.PostType
import com.ieum.domain.usecase.post.DeleteCommentUseCase
import com.ieum.domain.usecase.post.GetCommentListFlowUseCase
import com.ieum.domain.usecase.post.PostCommentUseCase
import com.ieum.domain.usecase.post.ReportCommentUseCase
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.CommentUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.model.post.ReportTypeUiModel
import com.ieum.presentation.screen.component.DropDownMenu
import com.ieum.presentation.util.ExceptionCollector
import com.ieum.presentation.util.MessageCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CommentBottomSheetState {
    data object Idle : CommentBottomSheetState()

    data class Show(
        private val scope: CoroutineScope,
        private val postId: Int,
        private val type: PostType,
        private val getCommentListFlowUseCase: GetCommentListFlowUseCase,
        private val postCommentUseCase: PostCommentUseCase,
        private val deleteCommentUseCase: DeleteCommentUseCase,
        private val reportCommentUseCase: ReportCommentUseCase,
    ) : CommentBottomSheetState() {
        sealed class CommentInnerSheetState {
            data object Idle : CommentInnerSheetState()

            data class ShowReportSheet(val commendId: Int) : CommentInnerSheetState()
        }

        var isLoading by mutableStateOf(false)
            private set

        var innerSheetState by mutableStateOf<CommentInnerSheetState>(CommentInnerSheetState.Idle)
            private set

        val typedCommentState = MaxLengthTextFieldState(maxLength = 500)

        val commentList: Flow<PagingData<CommentUiModel>> =
            getCommentListFlowUseCase(postId = postId, type = type)
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
                DropDownMenu.REPORT -> {
                    innerSheetState = CommentInnerSheetState.ShowReportSheet(commendId)
                }
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
                ).onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
                isLoading = false
            }
        }

        fun dismiss() {
            innerSheetState = CommentInnerSheetState.Idle
        }

        fun reportComment(commentId: Int, reportType: ReportTypeUiModel) {
            scope.launch {
                isLoading = true
                reportCommentUseCase(
                    postId = postId,
                    type = type,
                    commentId = commentId,
                    reportType = reportType.toDomain()
                ).onSuccess {
                    MessageCollector.sendMessage("신고가 접수됐어요")
                }.onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
                isLoading = false
            }
        }
    }
}

class CommentState @Inject constructor(
    private val getCommentListFlowUseCase: GetCommentListFlowUseCase,
    private val postCommentUseCase: PostCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val reportCommentUseCase: ReportCommentUseCase,
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
            getCommentListFlowUseCase = getCommentListFlowUseCase,
            postCommentUseCase = postCommentUseCase,
            deleteCommentUseCase = deleteCommentUseCase,
            reportCommentUseCase = reportCommentUseCase,
        )
    }
}