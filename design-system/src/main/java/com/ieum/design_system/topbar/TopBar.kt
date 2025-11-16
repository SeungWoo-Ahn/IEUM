package com.ieum.design_system.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.BackIcon
import com.ieum.design_system.icon.CloseIcon
import com.ieum.design_system.icon.IEUMIcon
import com.ieum.design_system.theme.screenPadding

private val topBarHeight = 52.dp

@Composable
fun TopBarForBack(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .padding(horizontal = 8.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onBack,
        ) {
            BackIcon()
        }
    }
}

@Composable
fun TopBarForClose(
    modifier: Modifier = Modifier,
    title: String,
    onClose: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .background(color = Color.Transparent)
            .padding(horizontal = 12.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onClose,
        ) {
            CloseIcon()
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
fun FeedTopBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .padding(horizontal = screenPadding)
    ) {
        IEUMIcon(
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}