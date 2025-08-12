package com.ieum.presentation.screen.main.postDailyRecords

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ieum.design_system.textfield.TextFieldState
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.util.ImageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDailyRecordsViewModel @Inject constructor(
    private val imageUtil: ImageUtil,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<MainScreen.PostDailyRecords>().id

    var uiState by mutableStateOf<PostDailyRecordsUiState>(PostDailyRecordsUiState.Idle)
        private set

    val titleState = TextFieldState()
    val storyState = TextFieldState()

    private val _imageList = mutableStateListOf<ImageSource>()
    val imageList: List<ImageSource> get() = _imageList

    var shareCommunity by mutableStateOf(false)
        private set

    init {
        // TODO: id != null (수정) 일 때, 데이터 로드
    }

    fun onPhotoPickerResult(uriList: List<Uri>) {
        viewModelScope.launch {
            val compressedImageList = mutableListOf<ImageSource.Local>()
            for (uri in uriList) {
                imageUtil.compressUriToFile(uri, 400, 400)
                    .onSuccess { file ->
                        compressedImageList += ImageSource.Local(file)
                    }
                if (imageList.size + compressedImageList.size == 3)
                    break
            }
            _imageList.addAll(compressedImageList)
        }
    }

    fun onDeleteImage(imageSource: ImageSource) {
        _imageList.remove(imageSource)
    }

    fun toggleShareCommunity() {
        shareCommunity = shareCommunity.not()
    }

    fun onPost() {
        viewModelScope.launch {
            uiState = PostDailyRecordsUiState.Loading
            // TODO: 비즈니스 로직 추가
        }
    }
}