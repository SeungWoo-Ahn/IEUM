package com.ieum.presentation.screen.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.checkbox.IEUMCheckBox
import com.ieum.design_system.icon.CommunityIcon
import com.ieum.design_system.icon.CompleteIcon
import com.ieum.design_system.icon.IncompleteIcon
import com.ieum.design_system.icon.MedicineIcon
import com.ieum.design_system.icon.PlusCircleIcon
import com.ieum.design_system.icon.ThunderIcon
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate500
import com.ieum.design_system.theme.Slate700
import com.ieum.presentation.R

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
        modifier = Modifier
            .padding(
                top = 2.dp,
                start = 28.dp
            ),
        text = guide,
        style = MaterialTheme.typography.bodySmall,
        color = Slate500,
    )
}

@Composable
private fun PostText(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = Slate500,
    )
}

@Composable
private fun PostSeparator() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        thickness = 1.dp,
        color = Slate100,
    )
}

@Composable
fun SpecificSymptomsBox(
    data: String,
    onClick: () -> Unit,
) {
    PostBox(
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.specific_symptoms),
                icon = { ThunderIcon() },
            )
            if (data.isBlank()) {
                PlusCircleIcon()
            }
        }
        if (data.isBlank()) {
            PostGuide(guide = stringResource(R.string.guide_specific_symptoms))
        } else {
            PostSeparator()
            PostText(text = data)
        }
    }
}

@Composable
private fun TakingMedicineInfo(data: Boolean) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (data) {
            CompleteIcon()
            Text(
                text = stringResource(R.string.complete),
                style = MaterialTheme.typography.bodySmall,
                color = Slate700,
            )
        } else {
            IncompleteIcon()
            Text(
                text = stringResource(R.string.incomplete),
                style = MaterialTheme.typography.bodySmall,
                color = Slate700,
            )
        }
    }
}

@Composable
fun TakingMedicineBox(
    data: Boolean?,
    onClick: () -> Unit,
) {
    PostBox(
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.taking_medicine),
                icon = { MedicineIcon() },
            )
            if (data == null) {
                PlusCircleIcon()
            } else {
                TakingMedicineInfo(data = data)
            }
        }
        if (data == null) {
            PostGuide(guide = stringResource(R.string.guide_taking_medicine))
        }
    }
}

@Composable
fun MemoBox(
    data: String,
    onClick: () -> Unit,
) {
    PostBox(
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.memo),
                icon = { ThunderIcon() },
            )
            if (data.isBlank()) {
                PlusCircleIcon()
            }
        }
        if (data.isBlank()) {
            PostGuide(guide = stringResource(R.string.guide_memo))
        } else {
            PostSeparator()
            PostText(text = data)
        }
    }
}

@Composable
fun ShareCommunityBox(
    data: Boolean,
    onClick: () -> Unit,
) {
    PostBox(
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.share_community),
                icon = { CommunityIcon() },
            )
            IEUMCheckBox(
                checked = data,
                onCheckedChange = {/*PostBox 에서*/}
            )
        }
        PostGuide(guide = stringResource(R.string.guide_share_community))
    }
}
