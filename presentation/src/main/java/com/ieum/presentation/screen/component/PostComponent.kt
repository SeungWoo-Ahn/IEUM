package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.checkbox.IEUMCheckBox
import com.ieum.design_system.icon.CloseCircleIcon
import com.ieum.design_system.icon.CommunityIcon
import com.ieum.design_system.icon.CompleteIcon
import com.ieum.design_system.icon.ImageIcon
import com.ieum.design_system.icon.IncompleteIcon
import com.ieum.design_system.icon.MealIcon
import com.ieum.design_system.icon.MedicineIcon
import com.ieum.design_system.icon.MemoIcon
import com.ieum.design_system.icon.MoodSelectIcon
import com.ieum.design_system.icon.PlusCircleIcon
import com.ieum.design_system.icon.RefreshBlackIcon
import com.ieum.design_system.icon.ThunderIcon
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate500
import com.ieum.design_system.theme.Slate700
import com.ieum.design_system.theme.White
import com.ieum.design_system.util.noRippleClickable
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.R
import com.ieum.presentation.model.post.AmountEatenUiModel
import com.ieum.presentation.model.post.DietUiModel
import com.ieum.presentation.model.post.MoodUiModel
import com.ieum.presentation.screen.main.postWellness.MAX_IMAGE_COUNT

@Composable
private fun PostBox(
    onClick: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(color = White)
            .border(
                width = 1.dp,
                color = Slate200,
                shape = MaterialTheme.shapes.medium,
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = 18.dp,
                vertical = 16.dp,
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
                top = 4.dp,
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
fun MoodBox(
    data: MoodUiModel?,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(108.dp)
            .noRippleClickable(onClick = onClick)
    ) {
        if (data == null) {
            MoodSelectIcon()
        } else {
            data.icon()
        }
        RefreshBlackIcon(
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun UnusualSymptomsBox(
    data: String,
    onClick: () -> Unit,
) {
    PostBox(onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.unusual_symptoms),
                icon = { ThunderIcon() },
            )
            if (data.isBlank()) {
                PlusCircleIcon()
            }
        }
        if (data.isBlank()) {
            PostGuide(guide = stringResource(R.string.guide_unusual_symptoms))
        } else {
            PostSeparator()
            PostText(text = data)
        }
    }
}

@Composable
fun MedicationTakenInfo(data: Boolean) {
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
    PostBox(onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.medication_taken),
                icon = { MedicineIcon() },
            )
            if (data == null) {
                PlusCircleIcon()
            } else {
                MedicationTakenInfo(data = data)
            }
        }
        if (data == null) {
            PostGuide(guide = stringResource(R.string.guide_medication_taken))
        }
    }
}

@Composable
fun AmountEatenInfo(data: AmountEatenUiModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        data.icon()
        Text(
            text = stringResource(data.description),
            style = MaterialTheme.typography.bodySmall,
            color = Slate700,
        )
    }
}

@Composable
fun DietBox(
    data: DietUiModel?,
    onClick: () -> Unit,
) {
    PostBox(onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.diet),
                icon = { MealIcon() }
            )
            if (data == null) {
                PlusCircleIcon()
            } else {
                AmountEatenInfo(data = data.amountEaten)
            }
        }
        if (data == null) {
            PostGuide(guide = stringResource(R.string.guide_diet))
        } else {
            PostSeparator()
            PostText(text = data.mealContent)
        }
    }
}

@Composable
fun MemoBox(
    data: String,
    onClick: () -> Unit,
) {
    PostBox(onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.memo),
                icon = { MemoIcon() },
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
fun AddImageBox(
    data: List<ImageSource>,
    maxImageCount: Int,
    onDeleteImage: (ImageSource) -> Unit,
    onClick: () -> Unit,
) {
    PostBox(onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.add_image),
                icon = { ImageIcon() },
            )
            PlusCircleIcon(enabled = data.size < maxImageCount)
        }
        PostGuide(guide = stringResource(R.string.guide_add_image, MAX_IMAGE_COUNT))
        if (data.isNotEmpty()) {
            IEUMSpacer(size = 10)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                data.forEach { source ->
                    PostImageItem(
                        source = source,
                        onDeleteImage = { onDeleteImage(source) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PostImageItem(
    source: ImageSource,
    onDeleteImage: () -> Unit,
) {
    Box(
        modifier = Modifier.size(95.dp)
    ) {
        IEUMNetworkImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.small),
            source = source
        )
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp),
                onClick = onDeleteImage
            ) {
                CloseCircleIcon()
            }
        }
    }
}

@Composable
fun ShareCommunityBox(
    data: Boolean,
    onClick: () -> Unit,
) {
    PostBox(onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PostInfo(
                name = stringResource(R.string.share_community),
                icon = { CommunityIcon() },
            )
            IEUMCheckBox(
                checked = data,
                onCheckedChange = { onClick() }
            )
        }
        PostGuide(guide = stringResource(R.string.guide_share_community))
    }
}
