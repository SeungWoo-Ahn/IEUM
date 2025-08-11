package com.ieum.presentation.screen.main.postTreatmentRecords

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForClose
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.R
import com.ieum.presentation.model.post.PostTreatmentRecordsUiModel
import com.ieum.presentation.screen.component.AddImageBox
import com.ieum.presentation.screen.component.DietaryStatusBox
import com.ieum.presentation.screen.component.DietaryStatusSheet
import com.ieum.presentation.screen.component.MemoBox
import com.ieum.presentation.screen.component.MemoSheet
import com.ieum.presentation.screen.component.ShareCommunityBox
import com.ieum.presentation.screen.component.SpecificSymptomsBox
import com.ieum.presentation.screen.component.SpecificSymptomsSheet
import com.ieum.presentation.screen.component.TakingMedicineBox
import com.ieum.presentation.screen.component.TakingMedicineDialog
import kotlinx.coroutines.CoroutineScope

internal const val MAX_IMAGE_COUNT = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostTreatmentRecordsRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    onBack: () -> Unit,
    viewModel: PostTreatmentRecordsViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val uiModel = viewModel.uiModel
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGE_COUNT),
        onResult = viewModel::onPhotoPickerResult,
    )

    PostTreatmentRecordsScreen(
        modifier = modifier,
        isLoading = uiState == PostTreatmentRecordsUiState.Loading,
        uiModel = uiModel,
        showSpecificSymptomsSheet = viewModel::showSpecificSymptomsSheet,
        showTakingMedicineDialog = viewModel::showTakingMedicineDialog,
        showDietaryStatusSheet = viewModel::showDietaryStatusSheet,
        showMemoSheet = viewModel::showMemoSheet,
        showAddImageSheet = {
            if (uiModel.imageList.size < MAX_IMAGE_COUNT) {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                        maxItems = MAX_IMAGE_COUNT - uiModel.imageList.size
                    )
                )
            }
        },
        onDeleteImage = viewModel::onDeleteImage,
        toggleShareCommunity = viewModel::toggleShareCommunity,
        onPost = viewModel::onPost,
        onBack = onBack,
    )
    if (uiState is PostTreatmentRecordsUiState.ShowSpecificSymptomsSheet) {
        SpecificSymptomsSheet(
            scope = scope,
            sheetState = sheetState,
            data = uiModel.specificSymptoms,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    if (uiState is PostTreatmentRecordsUiState.ShowTakingMedicineDialog) {
        TakingMedicineDialog(
            data = uiModel.takingMedicine,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    if (uiState is PostTreatmentRecordsUiState.ShowDietaryStatusSheet) {
        DietaryStatusSheet(
            scope = scope,
            sheetState = sheetState,
            data = uiModel.dietaryStatus,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    if (uiState is PostTreatmentRecordsUiState.ShowMemoSheet) {
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
private fun PostTreatmentRecordsScreen(
    modifier: Modifier,
    isLoading: Boolean,
    uiModel: PostTreatmentRecordsUiModel,
    showSpecificSymptomsSheet: () -> Unit,
    showTakingMedicineDialog: () -> Unit,
    showDietaryStatusSheet: () -> Unit,
    showMemoSheet: () -> Unit,
    showAddImageSheet: () -> Unit,
    onDeleteImage: (ImageSource) -> Unit,
    toggleShareCommunity: () -> Unit,
    onPost: () -> Unit,
    onBack: () -> Unit,
) {
    val buttonEnabled by remember { derivedStateOf { isLoading.not() && uiModel.validate() } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate100)
    ) {
        TopBarForClose(
            title = stringResource(R.string.treatment_records),
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
                SpecificSymptomsBox(
                    data = uiModel.specificSymptoms,
                    onClick = showSpecificSymptomsSheet,
                )
                TakingMedicineBox(
                    data = uiModel.takingMedicine,
                    onClick = showTakingMedicineDialog,
                )
                DietaryStatusBox(
                    data = uiModel.dietaryStatus,
                    onClick = showDietaryStatusSheet,
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
                    data = uiModel.shareCommunity,
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