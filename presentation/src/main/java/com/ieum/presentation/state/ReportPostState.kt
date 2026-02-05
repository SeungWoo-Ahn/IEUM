package com.ieum.presentation.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ieum.domain.model.post.PostType
import com.ieum.domain.usecase.post.ReportPostUseCase
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.model.post.ReportTypeUiModel
import com.ieum.presentation.util.ExceptionCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ReportPostBottomSheetState {
    data object Idle : ReportPostBottomSheetState()

    data class Show(
        private val scope: CoroutineScope,
        private val postId: Int,
        private val type: PostType,
        private val reportPostUseCase: ReportPostUseCase,
    ) : ReportPostBottomSheetState() {
        var isLoading by mutableStateOf(false)
            private set

        fun onReport(reportType: ReportTypeUiModel) {
            scope.launch {
                isLoading = true
                reportPostUseCase(
                    id = postId,
                    type = type,
                    reportType = reportType.toDomain()
                ).onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
                isLoading = false
            }
        }
    }
}

class ReportPostState @Inject constructor(
    private val reportPostUseCase: ReportPostUseCase,
) {
    var bottomSheetState by mutableStateOf<ReportPostBottomSheetState>(ReportPostBottomSheetState.Idle)
        private set

    fun dismiss() {
        bottomSheetState = ReportPostBottomSheetState.Idle
    }

    fun showSheet(post: PostUiModel, scope: CoroutineScope) {
        bottomSheetState = ReportPostBottomSheetState.Show(
            scope = scope,
            postId = post.id,
            type = post.type,
            reportPostUseCase = reportPostUseCase,
        )
    }
}