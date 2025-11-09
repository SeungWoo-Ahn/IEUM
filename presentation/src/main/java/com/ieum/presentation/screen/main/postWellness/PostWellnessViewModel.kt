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
import com.ieum.presentation.model.post.PostWellnessUiModel
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.util.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostWellnessViewModel @Inject constructor(
    private val imageUtil: ImageUtil,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<MainScreen.PostWellness>().id

    var uiState by mutableStateOf<PostWellnessUiState>(PostWellnessUiState.Idle)
        private set

    var uiModel by mutableStateOf(PostWellnessUiModel.EMPTY)
        private set

    init {
        // TODO: id != null (수정) 일 때, 데이터 로드
    }

    fun resetUiState() {
        uiState = PostWellnessUiState.Idle
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
            // TODO: 비즈니스 로직 추가
        }
    }
}