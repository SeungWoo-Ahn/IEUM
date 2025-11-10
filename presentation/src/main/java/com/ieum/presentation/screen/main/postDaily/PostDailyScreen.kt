package com.ieum.presentation.screen.main.postDaily

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.textfield.ITextFieldState
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForClose
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.R
import com.ieum.presentation.screen.component.AddImageBox
import com.ieum.presentation.screen.component.ShareCommunityBox
import com.ieum.presentation.screen.component.TypeContent
import com.ieum.presentation.screen.component.TypeTitle

internal const val MAX_IMAGE_COUNT = 3

@Composable
fun PostDailyRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: PostDailyViewModel = hiltViewModel(),
) {
    val buttonEnabled by remember { derivedStateOf { viewModel.validate() } }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGE_COUNT),
        onResult = viewModel::onPhotoPickerResult,
    )

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                PostDailyEvent.MoveBack -> onBack()
            }
        }
    }

    PostDailyScreen(
        modifier = modifier,
        buttonEnabled = buttonEnabled,
        titleState = viewModel.titleState,
        contentState = viewModel.contentState,
        imageList = viewModel.imageList,
        shareCommunity = viewModel.shareCommunity,
        showAddImageSheet = {
            if (viewModel.imageList.size < MAX_IMAGE_COUNT) {
                launcher.launch(
                    PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        },
        onDeleteImage = viewModel::onDeleteImage,
        toggleShareCommunity = viewModel::toggleShareCommunity,
        onPost = viewModel::onPost,
        onBack = onBack,
    )
}

@Composable
private fun PostDailyScreen(
    modifier: Modifier,
    buttonEnabled: Boolean,
    titleState: ITextFieldState,
    contentState: ITextFieldState,
    imageList: List<ImageSource>,
    shareCommunity: Boolean,
    showAddImageSheet: () -> Unit,
    onDeleteImage: (ImageSource) -> Unit,
    toggleShareCommunity: () -> Unit,
    onPost: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate100)
    ) {
        TopBarForClose(
            title = stringResource(R.string.daily_records),
            onClose = onBack,
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(state = rememberScrollState())
                .padding(all = screenPadding)
        ) {
            Column(
                modifier = Modifier.padding(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = White,
                            shape = MaterialTheme.shapes.medium,
                        )
                        .border(
                            width = 1.dp,
                            color = Slate200,
                            shape = MaterialTheme.shapes.medium,
                        )
                ) {
                    TypeTitle(state = titleState)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Slate200,
                    )
                    TypeContent(state = contentState)
                }
                AddImageBox(
                    data = imageList,
                    maxImageCount = MAX_IMAGE_COUNT,
                    onDeleteImage = onDeleteImage,
                    onClick = showAddImageSheet,
                )
                ShareCommunityBox(
                    data = shareCommunity,
                    onClick = toggleShareCommunity,
                )
            }
            DarkButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                text = stringResource(R.string.post),
                enabled = buttonEnabled,
                onClick = onPost,
            )
        }
    }
}