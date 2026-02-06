package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.RightIcon
import com.ieum.design_system.theme.Gray200
import com.ieum.design_system.theme.Gray500
import com.ieum.design_system.theme.White
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R

@Composable
fun SettingButton(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Gray200,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(all = 20.dp)
            .noRippleClickable(onClick = onClick),
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Composable
fun SettingPrivacyButton(
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val url = stringResource(R.string.privacy_policy_url)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = MaterialTheme.shapes.medium
            )
            .padding(all = 20.dp)
            .noRippleClickable { uriHandler.openUri(url) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.privacy_policy),
            style = MaterialTheme.typography.titleMedium,
        )
        RightIcon(color = Gray500)
    }
}