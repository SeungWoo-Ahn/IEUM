package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.PenIcon
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R

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