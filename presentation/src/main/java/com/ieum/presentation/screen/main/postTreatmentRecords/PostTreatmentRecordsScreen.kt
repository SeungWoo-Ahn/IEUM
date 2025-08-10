package com.ieum.presentation.screen.main.postTreatmentRecords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.DarkButton
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForClose
import com.ieum.domain.model.image.ImageSource
import com.ieum.presentation.R
import com.ieum.presentation.model.post.PostTreatmentRecordsUiModel
import com.ieum.presentation.screen.component.AddImageBox
import com.ieum.presentation.screen.component.DietaryStatusBox
import com.ieum.presentation.screen.component.MemoBox
import com.ieum.presentation.screen.component.ShareCommunityBox
import com.ieum.presentation.screen.component.SpecificSymptomsBox
import com.ieum.presentation.screen.component.TakingMedicineBox

@Composable
fun PostTreatmentRecordsRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    PostTreatmentRecordsScreen(
        modifier = modifier,
        isLoading = false,
        maxImageCount = 3,
        uiModel = PostTreatmentRecordsUiModel.EMPTY,
        showSpecificSymptomsSheet = {},
        showTakingMedicineDialog = {},
        showDietaryStatusSheet = {},
        showMemoSheet = {},
        showAddImageSheet = {},
        onDeleteImage = {},
        toggleShareCommunity = {},
        onPost = {},
        onBack = onBack,
    )
}

@Composable
private fun PostTreatmentRecordsScreen(
    modifier: Modifier,
    isLoading: Boolean,
    maxImageCount: Int,
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
        Column(
            modifier = Modifier.padding(all = screenPadding)
        ) {
            Column(
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
                    maxImageCount = maxImageCount,
                    onDeleteImage = onDeleteImage,
                    onClick = showAddImageSheet,
                )
                ShareCommunityBox(
                    data = uiModel.shareCommunity,
                    onClick = toggleShareCommunity,
                )
            }
            IEUMSpacer(modifier = Modifier.weight(1f))
            DarkButton(
                text = stringResource(R.string.post),
                enabled = buttonEnabled,
                onClick = onPost,
            )
        }
    }
}