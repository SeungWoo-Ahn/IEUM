package com.ieum.presentation.screen.auth.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ieum.design_system.button.BlackButton
import com.ieum.design_system.spacer.IEUMSpacer
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
                .padding(all = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            IEUMSpacer(size = 248)
            Text(
                text = stringResource(R.string.register_done),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            IEUMSpacer(size = 8)
            Text(
                text = stringResource(R.string.welcome_phrase),
                textAlign = TextAlign.Center,
                color = Color.Gray,
            )
            IEUMSpacer(modifier = Modifier.weight(1f))
            BlackButton(
                text = stringResource(R.string.start),
                onClick = moveMain,
            )
        }
    }
}