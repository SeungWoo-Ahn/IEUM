package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ieum.domain.usecase.user.GetMyPostListUseCase
import com.ieum.domain.usecase.user.GetMyProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getMyPostListUseCase: GetMyPostListUseCase,
) : ViewModel() {
    var currentTab by mutableStateOf(MyProfileTab.PROFILE)
        private set

    fun selectTab(tab: MyProfileTab) {
        currentTab = tab
    }
}