package com.ieum.presentation.screen.main.postWellness

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForClose
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.R
import com.ieum.presentation.model.post.PostWellnessUiModel
import com.ieum.presentation.screen.component.AddImageBox
import com.ieum.presentation.screen.component.DietBox
import com.ieum.presentation.screen.component.DietSheet
import com.ieum.presentation.screen.component.MedicationTakenDialog
import com.ieum.presentation.screen.component.MemoBox
import com.ieum.presentation.screen.component.MemoSheet
import com.ieum.presentation.screen.component.MoodBox
import com.ieum.presentation.screen.component.MoodDialog
import com.ieum.presentation.screen.component.ShareCommunityBox
import com.ieum.presentation.screen.component.TakingMedicineBox
import com.ieum.presentation.screen.component.UnusualSymptomsBox
import com.ieum.presentation.screen.component.UnusualSymptomsSheet
import kotlinx.coroutines.CoroutineScope

internal const val MAX_IMAGE_COUNT = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostWellnessRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    onBack: () -> Unit,
    viewModel: PostWellnessViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val uiModel = viewModel.uiModel
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGE_COUNT),
        onResult = viewModel::onPhotoPickerResult,
    )

    PostWellnessScreen(
        modifier = modifier,
        isLoading = uiState == PostWellnessUiState.Loading,
        uiModel = uiModel,
        showMoodDialog = viewModel::showMoodDialog,
        showUnusualSymptomsSheet = viewModel::showUnusualSymptomsSheet,
        showMedicationTakenDialog = viewModel::showMedicationTakenDialog,
        showDietSheet = viewModel::showDietSheet,
        showMemoSheet = viewModel::showMemoSheet,
        showAddImageSheet = {
            if (uiModel.imageList.size < MAX_IMAGE_COUNT) {
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
    if (uiState is PostWellnessUiState.ShowMoodDialog) {
        MoodDialog(
            scope = scope,
            data = uiModel.mood,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    if (uiState is PostWellnessUiState.ShowUnusualSymptomsSheet) {
        UnusualSymptomsSheet(
            scope = scope,
            sheetState = sheetState,
            data = uiModel.unusualSymptoms,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    if (uiState is PostWellnessUiState.ShowMedicationTakenDialog) {
        MedicationTakenDialog(
            data = uiModel.medicationTaken,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    if (uiState is PostWellnessUiState.ShowDietSheet) {
        DietSheet(
            scope = scope,
            sheetState = sheetState,
            data = uiModel.diet,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    if (uiState is PostWellnessUiState.ShowMemoSheet) {
        MemoSheet(
            scope = scope,
            sheetState = sheetState,
            data = uiModel.memo,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
}

@Composable
private fun PostWellnessScreen(
    modifier: Modifier,
    isLoading: Boolean,
    uiModel: PostWellnessUiModel,
    showMoodDialog: () -> Unit,
    showUnusualSymptomsSheet: () -> Unit,
    showMedicationTakenDialog: () -> Unit,
    showDietSheet: () -> Unit,
    showMemoSheet: () -> Unit,
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
            title = stringResource(R.string.wellness_records),
            onClose = onBack,
        )
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .padding(all = screenPadding)
                    .padding(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                MoodBox(
                    data = uiModel.mood,
                    onClick = showMoodDialog,
                )
                IEUMSpacer(size = 12)
                UnusualSymptomsBox(
                    data = uiModel.unusualSymptoms,
                    onClick = showUnusualSymptomsSheet,
                )
                TakingMedicineBox(
                    data = uiModel.medicationTaken,
                    onClick = showMedicationTakenDialog,
                )
                DietBox(
                    data = uiModel.diet,
                    onClick = showDietSheet,
                )
                MemoBox(
                    data = uiModel.memo,
                    onClick = showMemoSheet,
                )
                AddImageBox(
                    data = uiModel.imageList,
                    maxImageCount = MAX_IMAGE_COUNT,
                    onDeleteImage = onDeleteImage,
                    onClick = showAddImageSheet,
                )
                ShareCommunityBox(
                    data = uiModel.shared,
                    onClick = toggleShareCommunity,
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(all = screenPadding)
                    .padding(top = 8.dp)
            ) {
                DarkButton(
                    text = stringResource(R.string.post),
                    enabled = isLoading.not() && uiModel.validate(),
                    onClick = onPost,
                )
            }
        }
    }
}