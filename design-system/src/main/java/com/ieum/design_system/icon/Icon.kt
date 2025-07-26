package com.ieum.design_system.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.R

@Composable
fun IEUMIcon() {
    Icon(
        modifier = Modifier.size(
            width = 50.dp,
            height = 26.dp,
        ),
        painter = painterResource(R.drawable.ic_ieum),
        tint = Color.Unspecified,
        contentDescription = "ic-logo",
    )
}

@Composable
fun BackIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_back),
        contentDescription = "ic-back",
    )
}

@Composable
fun InfoCircleIcon() {
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.ic_info_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-info-circle",
    )
}

@Composable
fun CheckCircleIcon() {
    Icon(
        modifier = Modifier.size(88.dp),
        painter = painterResource(R.drawable.ic_check_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-check-circle",
    )
}