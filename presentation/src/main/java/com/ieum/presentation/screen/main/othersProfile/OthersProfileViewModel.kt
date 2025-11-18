package com.ieum.presentation.screen.main.othersProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ieum.domain.usecase.user.GetOthersProfileUseCase
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.navigation.MainScreen
import com.ieum.presentation.util.GlobalValueModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OthersProfileViewModel @Inject constructor(
    private val getOthersProfileUseCase: GetOthersProfileUseCase,
    private val valueModel: GlobalValueModel,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id = savedStateHandle.toRoute<MainScreen.OthersProfile>().id

    var uiState by mutableStateOf<OthersProfileUiState>(OthersProfileUiState.Loading)
        private set

    var currentTab by mutableStateOf(OthersProfileTab.PROFILE)
        private set

    init {
        getOthersProfile()
    }

    private fun getOthersProfile() {
        viewModelScope.launch {
            getOthersProfileUseCase(id)
                .onSuccess { profile ->
                    uiState = OthersProfileUiState.Success(
                        profile = profile.toUiModel(valueModel)
                    )
                }
                .onFailure {
                    // 불러오기 실패
                }
        }
    }

    fun selectTab(tab: OthersProfileTab) {
        currentTab = tab
    }
}