package com.ieum.presentation.screen.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.usecase.auth.LoginUseCase
import com.ieum.presentation.util.CustomException
import com.ieum.presentation.util.ExceptionCollector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val exceptionCollector: ExceptionCollector,
) : ViewModel() {
    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set

    private val _event = Channel<LoginEvent>()
    val event: Flow<LoginEvent> = _event.receiveAsFlow()

    fun onLogin(strategy: LoginStrategy) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading
            strategy.proceed()
                .onSuccess { oAuthRequest ->
                    login(oAuthRequest)
                }
                .onFailure {
                    exceptionCollector.sendException(CustomException("카카오 인증에 실패했습니다"))
                }
            uiState = LoginUiState.Idle
        }
    }

    private suspend fun login(oAuthRequest: OAuthRequest) {
        loginUseCase(oAuthRequest)
            .onSuccess { isRegistered ->
                if (isRegistered) {
                    _event.send(LoginEvent.MoveMain)
                } else {
                    _event.send(LoginEvent.MoveRegister)
                }
            }
            .onFailure { t ->
                exceptionCollector.sendException(t)
            }
    }
}