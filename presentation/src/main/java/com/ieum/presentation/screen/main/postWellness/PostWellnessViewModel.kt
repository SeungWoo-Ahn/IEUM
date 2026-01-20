package com.ieum.presentation.screen.main.postWellness

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.PostWellnessRequest
import com.ieum.domain.usecase.user.GetMyWellnessUseCase
import com.ieum.domain.usecase.post.PatchWellnessUseCase
import com.ieum.domain.usecase.post.PostWellnessUseCase
import com.ieum.presentation.mapper.toRequest
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.PostWellnessUiModel
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.util.CustomException
import com.ieum.presentation.util.ExceptionCollector
import com.ieum.presentation.util.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostWellnessViewModel @Inject constructor(
    private val getMyWellnessUseCase: GetMyWellnessUseCase,
    private val postWellnessUseCase: PostWellnessUseCase,
    private val patchWellnessUseCase: PatchWellnessUseCase,
    private val imageUtil: ImageUtil,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<MainScreen.PostWellness>().id

    var uiState by mutableStateOf<PostWellnessUiState>(PostWellnessUiState.Idle)
        private set

    var uiModel by mutableStateOf(PostWellnessUiModel.EMPTY)
        private set

    private val _event = Channel<PostWellnessEvent>()
    val event: Flow<PostWellnessEvent> = _event.receiveAsFlow()

    init {
        if (id != null) {
            loadWellness(id)
        }
    }

    private fun loadWellness(id: Int) {
        viewModelScope.launch {
            getMyWellnessUseCase(id)
                .onSuccess { wellness ->
                    uiModel = wellness.toUiModel()
                }
                .onFailure {
                    ExceptionCollector.sendException(CustomException("데이터 로드에 실패했습니다"))
                    _event.send(PostWellnessEvent.MoveBack)
                }
        }
    }

    fun resetUiState() {
        uiState = PostWellnessUiState.Idle
    }

    fun showMoodDialog() {
        uiState = PostWellnessUiState.ShowMoodDialog {
            uiModel = uiModel.copy(mood = it)
        }
    }

    fun showUnusualSymptomsSheet() {
        uiState = PostWellnessUiState.ShowUnusualSymptomsSheet {
            uiModel = uiModel.copy(unusualSymptoms = it)
        }
    }

    fun showMedicationTakenDialog() {
        uiState = PostWellnessUiState.ShowMedicationTakenDialog {
            uiModel = uiModel.copy(medicationTaken = it)
        }
    }

    fun showDietSheet() {
        uiState = PostWellnessUiState.ShowDietSheet {
            uiModel = uiModel.copy(diet = it)
        }
    }

    fun showMemoSheet() {
        uiState = PostWellnessUiState.ShowMemoSheet {
            uiModel = uiModel.copy(memo = it)
        }
    }

    fun onPhotoPickerResult(uriList: List<Uri>) {
        viewModelScope.launch {
            val compressedImageList = mutableListOf<ImageSource.Local>()
            for (uri in uriList) {
                imageUtil.compressUriToFile(uri, 400, 400)
                    .onSuccess { file ->
                        compressedImageList += ImageSource.Local(file)
                    }
                if (uiModel.imageList.size + compressedImageList.size == MAX_IMAGE_COUNT)
                    break
            }
            uiModel = uiModel.copy(imageList = uiModel.imageList + compressedImageList)
        }
    }

    fun onDeleteImage(imageSource: ImageSource) {
        uiModel = uiModel.copy(imageList = uiModel.imageList - imageSource)
    }

    fun toggleShareCommunity() {
        uiModel = uiModel.copy(shared = uiModel.shared.not())
    }

    fun onPost() {
        viewModelScope.launch {
            uiState = PostWellnessUiState.Loading
            val request = uiModel.toRequest()
            if (id == null) {
                postWellness(request)
            } else {
                patchWellness(id, request)
            }
        }
    }

    private suspend fun postWellness(request: PostWellnessRequest) {
        postWellnessUseCase(request)
            .onSuccess {
                _event.send(PostWellnessEvent.MoveBack)
            }
            .onFailure { t ->
                ExceptionCollector.sendException(t)
            }
    }

    private suspend fun patchWellness(id: Int, request: PostWellnessRequest) {
        patchWellnessUseCase(id, request)
            .onSuccess {
                _event.send(PostWellnessEvent.MoveBack)
            }
            .onFailure { t ->
                ExceptionCollector.sendException(t)
            }
    }
}