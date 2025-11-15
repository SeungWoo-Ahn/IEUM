package com.ieum.presentation.screen.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.ieum.design_system.icon.CommentIcon
import com.ieum.design_system.icon.HeartIcon
import com.ieum.design_system.icon.MealIcon
import com.ieum.design_system.icon.MedicineIcon
import com.ieum.design_system.icon.MemoIcon
import com.ieum.design_system.icon.MenuIcon
import com.ieum.design_system.icon.PenIcon
import com.ieum.design_system.icon.ThunderIcon
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate400
import com.ieum.design_system.theme.Slate600
import com.ieum.design_system.theme.Slate700
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.PostUserInfo
import com.ieum.presentation.R
import com.ieum.presentation.model.post.DiagnoseFilterUiModel
import com.ieum.presentation.model.post.DietUiModel
import com.ieum.presentation.model.post.MoodUiModel
import com.ieum.presentation.model.post.PostUiModel

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

@Composable
fun PostListArea(
    modifier: Modifier = Modifier,
    postList: LazyPagingItems<PostUiModel>,
    onNickname: (Int) -> Unit,
    onMenu: (Int) -> Unit,
    onLike: (Int) -> Unit,
    onComment: (Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            count = postList.itemCount,
            key = postList.itemKey { it.id }
        ) { index ->
            postList[index]?.let { post ->
                PostItem(
                    post = post,
                    onNickname = onNickname,
                    onMenu = { onMenu(post.id) },
                    onLike = { onLike(post.id) },
                    onComment = { onComment(post.id) }
                )
            }
        }
    }
}

@Composable
private fun PostItem(
    modifier: Modifier = Modifier,
    post: PostUiModel,
    onNickname: (Int) -> Unit,
    onMenu: () -> Unit,
    onLike: () -> Unit,
    onComment: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        PostItemTopBar(
            userInfo = post.userInfo,
            onNickname = onNickname,
            onMenu = onMenu,
        )
        post.imageList?.let {
            PostItemImageList(imageList = it)
        }
        PostItemIconList(
            onLike = onLike,
            onComment = onComment,
        )
        PostItemContent(post = post)
    }
}

@Composable
private fun PostItemTopBar(
    modifier: Modifier = Modifier,
    userInfo: PostUserInfo?,
    onNickname: (Int) -> Unit,
    onMenu: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = screenPadding,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .noRippleClickable {
                    if (userInfo != null) onNickname(userInfo.id)
                },
            contentAlignment = Alignment.CenterStart,
        ) {
            userInfo?.let {
                Text(
                    text = it.nickname,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
        Box(
            modifier = Modifier.noRippleClickable(onClick = onMenu)
        ) {
            MenuIcon()
        }
    }
}

@Composable
private fun PostItemImageList(
    modifier: Modifier = Modifier,
    imageList: List<ImageSource.Remote>,
) {
    val pagerState = rememberPagerState { imageList.size }
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
        ) { index ->
            IEUMNetworkImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                source = imageList[index]
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(imageList.size) { index ->
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(
                            color = if (index == pagerState.currentPage) {
                                White
                            } else {
                                Color(0x80D9D9D9)
                            },
                            shape = CircleShape,
                        )
                )
            }
        }
    }
}

@Composable
private fun PostItemIconList(
    modifier: Modifier = Modifier,
    onLike: () -> Unit,
    onComment: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.noRippleClickable(onClick = onLike)
        ) {
            HeartIcon()
        }
        Box(
            modifier = Modifier.noRippleClickable(onClick = onComment)
        ) {
            CommentIcon()
        }
    }
}

@Composable
private fun PostItemContent(
    modifier: Modifier = Modifier,
    post: PostUiModel,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (post) {
            is PostUiModel.Wellness -> WellnessContent(
                expanded = expanded,
                mood = post.mood,
                unusualSymptoms = post.unusualSymptoms,
                medicationTaken = post.medicationTaken,
                diet = post.diet,
                memo = post.memo,
            )
            is PostUiModel.Daily -> DailyContent(
                expanded = expanded,
                title = post.title,
                content = post.content,
            )
        }
        if (expanded.not()) {
            Text(
                modifier = Modifier.noRippleClickable { expanded = true },
                text = stringResource(R.string.see_more),
                style = MaterialTheme.typography.bodySmall,
                color = Slate600,
            )
        }
        Text(
            text = post.createdAt,
            style = MaterialTheme.typography.labelSmall,
            color = Slate400,
        )
    }
}

@Composable
private fun ContentLabel(
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
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 15.sp,
        )
    }
}

@Composable
private fun WellnessContent(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    mood: MoodUiModel,
    unusualSymptoms: String?,
    medicationTaken: Boolean,
    diet: DietUiModel?,
    memo: String?,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(24.dp)
            ) {
                mood.icon()
            }
            Text(
                text = stringResource(mood.description),
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 15.sp,
            )
        }
        unusualSymptoms?.let {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ContentLabel(
                    name = stringResource(R.string.unusual_symptoms),
                    icon = { ThunderIcon() }
                )
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate600,
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (expanded) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ContentLabel(
                    name = stringResource(R.string.medication_taken),
                    icon = { MedicineIcon() },
                )
                MedicationTakenInfo(data = medicationTaken)
            }
            diet?.let {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ContentLabel(
                            name = stringResource(R.string.diet),
                            icon = { MealIcon() }
                        )
                        AmountEatenInfo(data = it.amountEaten)
                    }
                    Text(
                        text = it.mealContent,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate600,
                    )
                }
            }
            memo?.let {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ContentLabel(
                        name = stringResource(R.string.memo),
                        icon = { MemoIcon() },
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate600,
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyContent(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    title: String,
    content: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall,
            color = Slate600,
            maxLines = if (expanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}