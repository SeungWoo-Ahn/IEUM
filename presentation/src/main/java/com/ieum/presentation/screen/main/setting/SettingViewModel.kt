package com.ieum.presentation.screen.main.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
                .onFailure {
                    // 로그아웃 실패
                }
        }
    }
}