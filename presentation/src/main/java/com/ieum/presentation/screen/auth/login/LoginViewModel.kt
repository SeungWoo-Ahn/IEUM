package com.ieum.presentation.screen.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event.asSharedFlow()

    fun onLogin(strategy: LoginStrategy) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading
            strategy.proceed()
                .onSuccess { oAuthRequest ->
                    Timber.i(oAuthRequest.toString())
                    _event.emit(LoginEvent.MoveRegister) // TODO: login 연결 이후 제거
                    /*login(oAuthRequest)*/
                }
                .onFailure { t ->
                    Timber.e(t)
                    // OAuth 로그인 실패
                }
            uiState = LoginUiState.Idle
        }
    }

    private suspend fun login(oAuthRequest: OAuthRequest) {
        loginUseCase(oAuthRequest)
            .onSuccess { isRegistered ->
                if (isRegistered) {
                    _event.emit(LoginEvent.MoveMain)
                } else {
                    _event.emit(LoginEvent.MoveRegister)
                }
            }
            .onFailure {
                // 로그인 API 실패
            }
    }
}