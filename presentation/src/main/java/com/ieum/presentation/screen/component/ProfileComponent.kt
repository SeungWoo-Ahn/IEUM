package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ieum.design_system.theme.Slate300
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.screen.main.othersProfile.OthersProfileTab

@Composable
private fun ProfileTabItem(
    name: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier.noRippleClickable(onClick = onClick),
        text = name,
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 20.sp,
        color = if (selected) Slate900 else Slate300,
        textDecoration = if (selected) TextDecoration.Underline else TextDecoration.None,
    )
}

@Composable
fun OthersProfileTabArea(
    modifier: Modifier = Modifier,
    currentTab: OthersProfileTab,
    onTabClick: (OthersProfileTab) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OthersProfileTab.entries.forEach { tab ->
            ProfileTabItem(
                name = stringResource(tab.displayName),
                selected = tab == currentTab,
                onClick = { onTabClick(tab) }
            )
        }
    }
}