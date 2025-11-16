package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.dialog.IEUMDialog
import com.ieum.design_system.icon.CompleteIcon
import com.ieum.design_system.icon.DailyIcon
import com.ieum.design_system.icon.IncompleteIcon
import com.ieum.design_system.icon.LeftIcon
import com.ieum.design_system.icon.RightIcon
import com.ieum.design_system.icon.WellnessIcon
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Black
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate500
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.dropShadow
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R
import com.ieum.presentation.model.post.MoodUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MoodDialog(
    scope: CoroutineScope,
    data: MoodUiModel?,
    callback: (MoodUiModel) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val entries = MoodUiModel.entries

    val pagerState = rememberPagerState(
        initialPage = if (data != null) {
            entries.indexOf(data)
        } else {
            entries.size / 2
        },
        pageCount = { entries.size }
    )

    IEUMDialog(onDismissRequest) {
        Column(
            modifier = Modifier
                .background(color = White)
                .padding(all = screenPadding)
        ) {
            PostSheetQuestion(
                question = stringResource(R.string.question_mood)
            )
            IEUMSpacer(size = 40)
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                HorizontalPager(state = pagerState) { page ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        MoodPagerItem(mood = entries[page])
                    }
                }
                MoodPagerScrollIcon(
                    modifier = Modifier.align(Alignment.CenterStart),
                    enabled = pagerState.canScrollBackward,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    icon = { LeftIcon() }
                )
                MoodPagerScrollIcon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    enabled = pagerState.canScrollForward,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    icon = { RightIcon() }
                )
            }
            IEUMSpacer(size = 40)
            DarkButton(
                text = stringResource(R.string.select_complete),
                onClick = {
                    callback(entries[pagerState.currentPage])
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
private fun MoodPagerItem(
    mood: MoodUiModel,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(
            modifier = Modifier.size(140.dp)
        ) {
            mood.icon()
        }
        Box(
            modifier = Modifier
                .background(
                    color = mood.backGroundColor,
                    shape = RoundedCornerShape(size = 40.dp)
                )
                .border(
                    width = 1.dp,
                    color = mood.strokeColor,
                    shape = RoundedCornerShape(size = 40.dp),
                )
                .padding(
                    horizontal = 14.dp,
                    vertical = 8.dp
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(mood.description),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun MoodPagerScrollIcon(
    modifier: Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    icon: @Composable ()-> Unit
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .background(
                color = if (enabled) Black.copy(alpha = 0.4f) else Black.copy(alpha = 0.1f),
                shape = CircleShape,
            )
            .noRippleClickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        icon()
    }
}

@Composable
fun MedicationTakenDialog(
    data: Boolean?,
    callback: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
) {
    IEUMDialog(onDismissRequest) {
        Column(
            modifier = Modifier
                .background(color = White)
                .padding(all = screenPadding)
        ) {
            PostSheetQuestion(
                question = stringResource(R.string.question_medication_taken)
            )
            IEUMSpacer(size = 32)
            MedicationTakenSelector(
                name = stringResource(R.string.complete),
                icon = { CompleteIcon() },
                isSelected = data == true,
                onClick = {
                    callback(true)
                    onDismissRequest()
                }
            )
            IEUMSpacer(size = 12)
            MedicationTakenSelector(
                name = stringResource(R.string.incomplete),
                icon = { IncompleteIcon() },
                isSelected = data == false,
                onClick = {
                    callback(false)
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
private fun MedicationTakenSelector(
    name: String,
    icon: @Composable () -> Unit,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isSelected) Lime500 else Slate200,
                shape = MaterialTheme.shapes.medium
            )
            .noRippleClickable(onClick = onClick)
            .padding(
                horizontal = 10.dp,
                vertical = 16.dp
            ),
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
fun AddPostDialog(
    movePostWellness: () -> Unit,
    movePostDaily: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable(onClick = onDismissRequest)
            .padding(
                horizontal = screenPadding,
                vertical = 90.dp
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = White,
                    shape = MaterialTheme.shapes.medium,
                )
                .dropShadow()
                .padding(all = screenPadding),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            AddPostItem(
                name = stringResource(R.string.wellness_records),
                guide = stringResource(R.string.guide_wellness_records),
                icon = { WellnessIcon() },
                onClick = {
                    movePostWellness()
                    onDismissRequest()
                }
            )
            AddPostItem(
                name = stringResource(R.string.daily_records),
                guide = stringResource(R.string.guide_daily_records),
                icon = { DailyIcon() },
                onClick = {
                    movePostDaily()
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
private fun AddPostItem(
    name: String,
    guide: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Text(
            modifier = Modifier.padding(start = 28.dp),
            text = guide,
            style = MaterialTheme.typography.bodyMedium,
            color = Slate500,
        )
    }
}