package com.ieum.presentation.screen.auth.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.BlackButton

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    moveMain: () -> Unit,
    moveRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LoginScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onLogin = viewModel::onLogin,
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier,
    uiState: LoginUiState,
    onLogin: (LoginStrategy) -> Unit,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 20.dp)
    ) {
        BlackButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = "카카오 로그인",
            enabled = uiState != LoginUiState.Loading,
            onClick = { onLogin(LoginStrategy.KaKao(context)) }
        )
    }
}