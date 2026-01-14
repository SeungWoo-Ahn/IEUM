package com.ieum.presentation.screen.main.postDaily

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
import com.ieum.domain.model.post.PostDailyRequest
import com.ieum.domain.usecase.post.GetDailyUseCase
import com.ieum.domain.usecase.post.PatchDailyUseCase
import com.ieum.domain.usecase.post.PostDailyUseCase
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
class PostDailyViewModel @Inject constructor(
    private val getDailyUseCase: GetDailyUseCase,
    private val postDailyUseCase: PostDailyUseCase,
    private val patchDailyUseCase: PatchDailyUseCase,
    private val imageUtil: ImageUtil,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<MainScreen.PostDaily>().id

    private var uiState by mutableStateOf<PostDailyUiState>(PostDailyUiState.Idle)

    private val _event = Channel<PostDailyEvent>()
    val event: Flow<PostDailyEvent> = _event.receiveAsFlow()

    val titleState = TextFieldState()
    val contentState = TextFieldState()

    private val _imageList = mutableStateListOf<ImageSource>()
    val imageList: List<ImageSource> get() = _imageList

    var shareCommunity by mutableStateOf(false)
        private set

    init {
        if (id != null) {
            loadDaily(id)
        }
    }

    private fun loadDaily(id: Int) {
        viewModelScope.launch {
            getDailyUseCase(id)
                .onSuccess { daily ->
                    titleState.typeText(daily.title)
                    contentState.typeText(daily.content)
                    daily.imageList?.let { _imageList.addAll(it) }
                    shareCommunity = daily.shared
                }
                .onFailure {
                    ExceptionCollector.sendException(CustomException("데이터 로드에 실패했습니다"))
                    _event.send(PostDailyEvent.MoveBack)
                }
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
                if (imageList.size + compressedImageList.size == MAX_IMAGE_COUNT)
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

    fun validate() = uiState != PostDailyUiState.Loading &&
            titleState.validate() &&
            contentState.validate()

    fun onPost() {
        viewModelScope.launch {
            uiState = PostDailyUiState.Loading
            val request = PostDailyRequest(
                title = titleState.getTrimmedText(),
                content = contentState.getTrimmedText(),
                imageList = imageList.filterIsInstance<ImageSource.Local>(),
                shared = shareCommunity,
            )
            if (id == null) {
                postDaily(request)
            } else {
                patchDaily(id, request)
            }
            uiState = PostDailyUiState.Idle
        }
    }

    private suspend fun postDaily(request: PostDailyRequest) {
        postDailyUseCase(request)
            .onSuccess {
                _event.send(PostDailyEvent.MoveBack)
            }
            .onFailure { t ->
                ExceptionCollector.sendException(t)
            }
    }

    private suspend fun patchDaily(id: Int, request: PostDailyRequest) {
        patchDailyUseCase(id, request)
            .onSuccess {
                _event.send(PostDailyEvent.MoveBack)
            }
            .onFailure { t ->
                ExceptionCollector.sendException(t)
            }
    }
}