package com.ieum.presentation.screen.auth.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.icon.CheckCircleIcon
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Gray500
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.presentation.R

@Composable
fun WelcomeRoute(
    modifier: Modifier = Modifier,
    moveMain: () -> Unit,
) {
    WelcomeScreen(
        modifier = modifier,
        moveMain = moveMain,
    )
    BackHandler(onBack = moveMain)
}

@Composable
private fun WelcomeScreen(
    modifier: Modifier,
    moveMain: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBarForBack(onBack = moveMain)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = screenPadding,
                    vertical = 16.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IEUMSpacer(size = 120)
            CheckCircleIcon()
            IEUMSpacer(size = 24)
            Text(
                text = stringResource(R.string.register_done),
                style = MaterialTheme.typography.headlineLarge,
            )
            IEUMSpacer(size = 8)
            Text(
                text = stringResource(R.string.welcome_phrase),
                style = MaterialTheme.typography.bodyMedium,
                color = Gray500,
                textAlign = TextAlign.Center,
            )
            IEUMSpacer(modifier = Modifier.weight(1f))
            DarkButton(
                text = stringResource(R.string.start),
                onClick = moveMain,
            )
        }
    }
}