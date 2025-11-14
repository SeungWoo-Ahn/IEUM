package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.PenIcon
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate700
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R
import com.ieum.presentation.model.post.DiagnoseFilterUiModel

@Composable
fun WriteFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(
                color = Slate900,
                shape = RoundedCornerShape(size = 40.dp),
            )
            .padding(all = 12.dp)
            .noRippleClickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
       PenIcon()
       Text(
           text = stringResource(R.string.write),
           style = MaterialTheme.typography.titleLarge,
           color = White,
       )
    }
}

@Composable
fun DiagnoseFilterArea(
    modifier: Modifier = Modifier,
    selectedFilter: DiagnoseFilterUiModel,
    onFilter: (DiagnoseFilterUiModel) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState())
            .padding(
                horizontal = screenPadding,
                vertical = 16.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DiagnoseFilterUiModel.entries.forEach { filter ->
            DiagnoseFilter(
                selected = filter == selectedFilter,
                name = stringResource(filter.displayName),
                onClick = { onFilter(filter) }
            )
        }
    }
}

@Composable
private fun DiagnoseFilter(
    modifier: Modifier = Modifier,
    selected: Boolean,
    name: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                color = if (selected) Slate900 else Slate100,
                shape = RoundedCornerShape(size = 30.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp,
            )
            .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) White else Slate700
        )
    }
}