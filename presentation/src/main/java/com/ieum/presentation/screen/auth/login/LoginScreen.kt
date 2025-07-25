package com.ieum.presentation.screen.auth.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.BlackButton
import com.ieum.design_system.icon.IEUMIcon
import com.ieum.design_system.image.LoginBackGroundImage
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.screenPadding
import com.ieum.presentation.R

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    moveMain: () -> Unit,
    moveRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                LoginEvent.MoveMain -> moveMain()
                LoginEvent.MoveRegister -> moveRegister()
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        buttonEnabled = viewModel.uiState != LoginUiState.Loading,
        onLogin = viewModel::onLogin,
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier,
    buttonEnabled: Boolean,
    onLogin: (LoginStrategy) -> Unit,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LoginBackGroundImage()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 60.dp,
                    bottom = 16.dp,
                    start = screenPadding,
                    end = screenPadding,
                )
        ) {
            IEUMIcon()
            IEUMSpacer(size = 18)
            Text(
                text = stringResource(R.string.catch_phrase),
                style = MaterialTheme.typography.headlineLarge,
            )
            IEUMSpacer(modifier = Modifier.weight(1f))
            BlackButton(
                text = stringResource(R.string.login_with_kakao),
                enabled = buttonEnabled,
                onClick = { onLogin(LoginStrategy.KaKao(context)) }
            )
        }
    }
}