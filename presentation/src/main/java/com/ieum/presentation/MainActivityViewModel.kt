package com.ieum.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.domain.usecase.auth.GetTokenFlowUseCase
import com.ieum.domain.usecase.auth.HealthCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val healthCheckUseCase: HealthCheckUseCase,
    getTokenFlowUseCase: GetTokenFlowUseCase,
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> =
        getTokenFlowUseCase()
            .map { MainActivityUiState.Success(isAuthenticated = it != null) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MainActivityUiState.Loading,
            )

    init {
        healthCheck()
    }

    private fun healthCheck() {
        viewModelScope.launch {
            healthCheckUseCase()
        }
    }
}

sealed class MainActivityUiState {
    data object Loading : MainActivityUiState()

    data class Success(val isAuthenticated: Boolean) : MainActivityUiState()

    val shouldKeepSplashScreen get() = this is Loading
}