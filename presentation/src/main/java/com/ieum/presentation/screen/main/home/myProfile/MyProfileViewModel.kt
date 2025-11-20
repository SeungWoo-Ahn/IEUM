package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.usecase.user.GetMyPostListUseCase
import com.ieum.domain.usecase.user.GetMyProfileUseCase
import com.ieum.presentation.util.GlobalValueModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun selectTab(tab: MyProfileTab) {
        currentTab = tab
    }
}