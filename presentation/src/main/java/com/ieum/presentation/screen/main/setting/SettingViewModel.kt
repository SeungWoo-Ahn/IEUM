package com.ieum.presentation.screen.main.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var dialogState by mutableStateOf<SettingDialogState>(SettingDialogState.Idle)
        private set

    fun onLogout() {
        viewModelScope.launch {
            logoutUseCase()
                .onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
        }
    }

    fun dismiss() {
        dialogState = SettingDialogState.Idle
    }

    fun onWithdraw() {
        dialogState = SettingDialogState.ShowWithdrawDialog
    }

    fun withdraw() {
        viewModelScope.launch {
            withdrawUseCase()
                .onFailure { t ->
                    ExceptionCollector.sendException(t)
                }
        }
    }
}