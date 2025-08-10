package com.ieum.presentation.screen.main.postTreatmentRecords

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.model.post.PostTreatmentRecordsUiModel
import com.ieum.presentation.navigation.MainScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostTreatmentRecordsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<MainScreen.PostTreatmentRecords>().id

    var uiState by mutableStateOf<PostTreatmentRecordsUiState>(PostTreatmentRecordsUiState.Idle)
        private set

    var uiModel by mutableStateOf(PostTreatmentRecordsUiModel.EMPTY)
        private set

    init {
        // TODO: id != null (수정) 일 때, 데이터 로드
    }

    fun dismiss() {
        uiState = PostTreatmentRecordsUiState.Idle
    }

    fun showSpecificSymptomsSheet() {
        uiState = PostTreatmentRecordsUiState.ShowSpecificSymptomsSheet(
            data = uiModel.specificSymptoms,
            callback = { uiModel = uiModel.copy(specificSymptoms = it) }
        )
    }

    fun showTakingMedicineDialog() {
        uiState = PostTreatmentRecordsUiState.ShowTakingMedicineDialog(
            data = uiModel.takingMedicine,
            callback = { uiModel = uiModel.copy(takingMedicine = it) }
        )
    }

    fun showDietaryStatusSheet() {
        uiState = PostTreatmentRecordsUiState.ShowDietaryStatusSheet(
            data = uiModel.dietaryStatus,
            callback = { uiModel = uiModel.copy(dietaryStatus = it) }
        )
    }

    fun showMemoSheet() {
        uiState = PostTreatmentRecordsUiState.ShowMemoSheet(
            data = uiModel.memo,
            callback = { uiModel = uiModel.copy(memo = it) }
        )
    }

    fun onPhotoPickerResult(uriList: List<Uri>) {
        viewModelScope.launch {

        }
    }

    fun onDeleteImage(imageSource: ImageSource) {
        uiModel = uiModel.copy(imageList = uiModel.imageList - imageSource)
    }

    fun toggleShareCommunity() {
        uiModel = uiModel.copy(shareCommunity = uiModel.shareCommunity.not())
    }

    fun onPost() {
        viewModelScope.launch {
            uiState = PostTreatmentRecordsUiState.Loading
            // TODO: 비즈니스 로직 추가
        }
    }
}