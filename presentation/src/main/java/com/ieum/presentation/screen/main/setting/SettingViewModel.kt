package com.ieum.presentation.screen.main.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.usecase.auth.LogoutUseCase
import com.ieum.domain.usecase.user.WithdrawUseCase
import com.ieum.presentation.util.ExceptionCollector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
) : ViewModel() {
    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
                .onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
        }
    }

    fun onWithdraw() {
        viewModelScope.launch {
            withdrawUseCase()
                .onSuccess {

                }
                .onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
        }
    }
}