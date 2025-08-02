package com.ieum.presentation.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate500

@Composable
private fun PostBox(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = Slate200,
                shape = MaterialTheme.shapes.medium,
            )
            .padding(
                horizontal = 18.dp,
                vertical = 12.dp,
            ),
        content = content,
    )
}

@Composable
private fun PostInfo(
    name: String,
    icon: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun PostGuide(
    guide: String,
) {
    Text(
        modifier = Modifier.padding(start = 28.dp),
        text = guide,
        style = MaterialTheme.typography.bodySmall,
        color = Slate500
    )
}
