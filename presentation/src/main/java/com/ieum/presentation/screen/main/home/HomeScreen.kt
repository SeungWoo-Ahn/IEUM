package com.ieum.presentation.screen.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ieum.design_system.theme.Slate300
import com.ieum.design_system.theme.Slate500
import com.ieum.design_system.theme.Slate950
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.Post
import com.ieum.presentation.screen.component.AddPostDialog
import com.ieum.presentation.screen.component.IEUMNetworkImage

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    movePostTreatmentRecords: () -> Unit,
    movePostDailyRecords: () -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        movePostTreatmentRecords = movePostTreatmentRecords,
        movePostDailyRecords = movePostDailyRecords,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    movePostTreatmentRecords: () -> Unit,
    movePostDailyRecords: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val postList by viewModel.postList.collectAsStateWithLifecycle()
    var showAddPostDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(
                items = postList,
                key = { it.id },
                contentType = { it }
            ) { post ->
                when (post) {
                    is Post.TreatmentRecords -> TreatmentRecordsItem(post)
                    is Post.DailyRecords -> DailyRecordsItem(post)
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Slate300
                )
            }
        }
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = screenPadding),
            expanded = true,
            text = { Text(text = "글쓰기") },
            icon = { Icon(imageVector = Icons.Rounded.Add, contentDescription = "ic-add") },
            containerColor = Slate950,
            contentColor = White,
            onClick = { showAddPostDialog = true }
        )
        if (showAddPostDialog) {
            AddPostDialog(
                movePostTreatmentRecords = movePostTreatmentRecords,
                movePostDailyRecords = movePostDailyRecords,
                onDismissRequest = { showAddPostDialog = false }
            )
        }
    }
}

@Composable
private fun TreatmentRecordsItem(
    post: Post.TreatmentRecords
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (post.imageList.isNotEmpty()) {
            PostImageList(imageList = post.imageList)
        }
        post.specificSymptoms?.let {
            Text(text = it)
        }
        Text(text = post.dietary.content)
        post.memo?.let {
            Text(text = it)
        }
    }
}

@Composable
private fun DailyRecordsItem(
    post: Post.DailyRecords
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (post.imageList.isNotEmpty()) {
            PostImageList(imageList = post.imageList)
        }
        Text(
            text = post.title,
            style = MaterialTheme.typography.headlineSmall,
        )
        post.story?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = Slate500
            )
        }
    }
}

@Composable
private fun PostImageList(
    imageList: List<ImageSource>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        imageList.forEach {
            IEUMNetworkImage(
                modifier = Modifier.weight(1f),
                source = it
            )
        }
    }
}

