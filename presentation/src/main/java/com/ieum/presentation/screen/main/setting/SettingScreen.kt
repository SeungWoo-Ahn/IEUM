package com.ieum.presentation.screen.main.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Slate50
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.presentation.R
import com.ieum.presentation.screen.component.SettingButton

@Composable
fun SettingRoute(
    modifier: Modifier = Modifier,
    moveBack: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    SettingScreen(
        modifier = modifier,
        onLogout = viewModel::onLogout,
        onWithdraw = viewModel::onWithdraw,
        moveBack = moveBack,
    )
}

@Composable
private fun SettingScreen(
    modifier: Modifier,
    onLogout: () -> Unit,
    onWithdraw: () -> Unit,
    moveBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Slate50)
    ) {
        TopBarForBack(
            title = stringResource(R.string.setting),
            onBack = moveBack,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = screenPadding,
                    vertical = 40.dp
                )
        ) {
            IEUMSpacer(modifier = Modifier.weight(1f))
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                SettingButton(
                    name = stringResource(R.string.logout),
                    onClick = onLogout,
                )
                SettingButton(
                    name = stringResource(R.string.withdraw),
                    onClick = onWithdraw,
                )
            }
        }
    }
}