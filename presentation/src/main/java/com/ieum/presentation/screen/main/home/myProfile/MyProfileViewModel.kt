package com.ieum.presentation.screen.main.home.myProfile

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
import com.ieum.domain.usecase.user.GetMyPostListUseCase
import com.ieum.domain.usecase.user.GetMyProfileUseCase
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.post.PostTypeUiModel
import com.ieum.presentation.model.post.PostUiModel
import com.ieum.presentation.util.GlobalValueModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getMyPostListUseCase: GetMyPostListUseCase,
    val valueModel: GlobalValueModel,
) : ViewModel() {
    var uiState by mutableStateOf<MyProfileUiState>(MyProfileUiState.Loading)
        private set

    var currentTab by mutableStateOf(MyProfileTab.PROFILE)
        private set

    private val _postType = MutableStateFlow(PostTypeUiModel.WELLNESS)
    val postType: StateFlow<PostTypeUiModel> get() = _postType

    val postListFlow: Flow<PagingData<PostUiModel>> =
        postType
            .flatMapLatest { type ->
                Pager(
                    config = PagingConfig(pageSize = 5),
                    pagingSourceFactory = { MyPostPagerSource(
                        getMyPostListUseCase = getMyPostListUseCase,
                        postType = type.toDomain(),
                    ) }
                )
                    .flow
            }
            .map { pagingData ->
                pagingData.map(Post::toUiModel)
            }
            .cachedIn(viewModelScope)

    init {
        getMyProfile()
    }

    private fun getMyProfile() {
        viewModelScope.launch {
            getMyProfileUseCase()
                .onSuccess {
                    uiState = MyProfileUiState.Success(it)
                }
                .onFailure {
                    // 불러오기 실패
                }
        }
    }

    fun onTab(tab: MyProfileTab) {
        currentTab = tab
    }

    fun onPostType(type: PostTypeUiModel) {
        _postType.update { type }
    }
}