package com.ieum.presentation.screen.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.model.auth.OAuthRequest
import com.ieum.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    var uiState by mutableStateOf<LoginUiState>(LoginUiState.Idle)
        private set

    fun onLogin(strategy: LoginStrategy) {
        uiState = LoginUiState.Loading
        strategy.proceed()
            .onSuccess { oAuthRequest ->
                executeLogin(oAuthRequest)
            }
            .onFailure {
                // OAuth 로그인 실패
            }
        uiState = LoginUiState.Idle
    }

    private fun executeLogin(oAuthRequest: OAuthRequest) {
        viewModelScope.launch {
            loginUseCase(oAuthRequest)
                .onSuccess { isRegistered ->
                    if (isRegistered) {
                        // TODO: 메인 화면 이동
                    } else {
                        // TODO: 회원가입 화면 이동
                    }
                }
                .onFailure {
                    // 로그인 API 실패
                }
        }
    }
}