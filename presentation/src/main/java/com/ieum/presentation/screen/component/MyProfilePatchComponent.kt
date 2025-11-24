package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.Lime400Button
import com.ieum.design_system.checkbox.IEUMCheckBox
import com.ieum.design_system.dialog.FullScreenDialog
import com.ieum.design_system.icon.LockIcon
import com.ieum.design_system.selector.SingleSelectorState
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate400
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.design_system.util.noRippleClickable
import com.ieum.domain.model.user.MyProfile
import com.ieum.domain.model.user.ProfileProperty
import com.ieum.presentation.R
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel
import com.ieum.presentation.screen.main.home.myProfile.PatchMyProfile
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.DiagnoseState
import kotlinx.coroutines.CoroutineScope

@Composable
private fun PatchLockCheckBox(
    isLocked: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = Slate100,
                    shape = MaterialTheme.shapes.large,
                )
                .border(
                    width = 1.dp,
                    color = Slate200,
                    shape = MaterialTheme.shapes.large,
                )
                .noRippleClickable(onClick = onClick)
                .padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LockIcon()
            IEUMSpacer(size = 2)
            Text(
                text = stringResource(R.string.not_opened),
                style = MaterialTheme.typography.labelSmall,
                color = Slate400,
            )
            IEUMSpacer(size = 6)
            IEUMCheckBox(
                checked = isLocked,
                onCheckedChange = { onClick() }
            )
        }
    }
}

private sealed class PatchDiagnoseSheetState {
    data object Idle : PatchDiagnoseSheetState()

    data class ShowCancerStageSheet(
        val data: CancerDiagnoseUiModel,
        val callback: (CancerStageUiModel) -> Unit
    ) : PatchDiagnoseSheetState()
}

@Composable
fun PatchDiagnoseDialog(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    profile: MyProfile,
    patch: PatchMyProfile,
    onDismissRequest: () -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }
    var isOpened by remember { mutableStateOf(profile.diagnoses.open) }
    val state = remember { DiagnoseState().apply {
        profile.diagnoses.data?.let { setDiagnoseList(it) }
    } }
    var sheetState by remember {
        mutableStateOf<PatchDiagnoseSheetState>(PatchDiagnoseSheetState.Idle)
    }
    val buttonEnabled by remember { derivedStateOf { isLoading.not() && state.validate() } }

    fun showCancerStageSheet(cancerDiagnose: CancerDiagnoseUiModel) {
        sheetState = PatchDiagnoseSheetState.ShowCancerStageSheet(data = cancerDiagnose) {
            state.cancerDiagnoseState.onDiagnose(cancerDiagnose.copy(stage = it))
        }
    }

    fun dismissSheet() {
        sheetState = PatchDiagnoseSheetState.Idle
    }

    fun patchDiagnose() {
        val diagnose = ProfileProperty(
            data = state.getSelectedDiagnoseList(),
            open = isOpened,
        )
        if (diagnose != profile.diagnoses) {
            isLoading = true
            val patchedProfile = profile.copy(diagnoses = diagnose)
            patch(patchedProfile) { isLoading = false }
        } else {
            onDismissRequest()
        }
    }

    FullScreenDialog(onDismissRequest) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TopBarForBack(onBack = onDismissRequest)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenPadding)
                    .padding(bottom = 16.dp),
            ) {
                UserGuideArea(guide = stringResource(R.string.guide_select_diagnose))
                IEUMSpacer(size = 40)
                SelectDiagnose(
                    state = state,
                    showCancerStageSheet = ::showCancerStageSheet,
                )
                IEUMSpacer(modifier = Modifier.weight(1f))
                PatchLockCheckBox(
                    isLocked = isOpened.not(),
                    onClick = { isOpened = isOpened.not() },
                )
                IEUMSpacer(size = 14)
                SelectedCountButton(
                    text = stringResource(R.string.complete),
                    enabled = buttonEnabled,
                    selectedCount = state.totalSelectedCount,
                    onClick = ::patchDiagnose,
                )
            }
        }
        val currentSheetState = sheetState
        if (currentSheetState is PatchDiagnoseSheetState.ShowCancerStageSheet) {
            CancerStageSheet(
                scope = scope,
                data = currentSheetState.data,
                callback = currentSheetState.callback,
                onDismissRequest = ::dismissSheet,
            )
        }
    }
}

@Composable
fun PatchAgeGroupDialog(
    modifier: Modifier = Modifier,
    profile: MyProfile,
    patch: PatchMyProfile,
    onDismissRequest: () -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }
    var isOpened by remember { mutableStateOf(profile.ageGroup.open) }
    val state = remember {
        SingleSelectorState(itemList = AgeGroupUiModel.entries).apply {
            profile.ageGroup.data?.let { setItem(it.toUiModel()) }
        }
    }

    fun patchAgeGroup() {
        val ageGroup = ProfileProperty(
            data = state.selectedItem?.toDomain(),
            open = isOpened,
        )
        if (ageGroup != profile.ageGroup) {
            val patchedProfile = profile.copy(ageGroup = ageGroup)
            isLoading = true
            patch(patchedProfile) { isLoading = false }
        } else {
            onDismissRequest()
        }
    }

    FullScreenDialog(onDismissRequest) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TopBarForBack(onBack = onDismissRequest)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenPadding)
                    .padding(bottom = 16.dp),
            ) {
                UserGuideArea(guide = stringResource(R.string.guide_select_age_group))
                IEUMSpacer(size = 40)
                SelectAgeGroup(state = state)
                IEUMSpacer(modifier = Modifier.weight(1f))
                PatchLockCheckBox(
                    isLocked = isOpened.not(),
                    onClick = { isOpened = isOpened.not() },
                )
                IEUMSpacer(size = 14)
                Lime400Button(
                    text = stringResource(R.string.complete),
                    enabled = isLoading.not(),
                    onClick = ::patchAgeGroup,
                )
            }
        }
    }
}

@Composable
fun PatchResidenceDialog(
    modifier: Modifier = Modifier,
    profile: MyProfile,
    state: AddressState,
    patch: PatchMyProfile,
    onDismissRequest: () -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }
    var isOpened by remember { mutableStateOf(profile.residenceArea.open) }

    fun patchResidence() {
        val residenceArea = ProfileProperty(
            data = state.getSelectedProvince()?.fullName,
            open = isOpened,
        )
        if (residenceArea != profile.residenceArea) {
            val patchedProfile = profile.copy(residenceArea = residenceArea)
            isLoading = true
            patch(patchedProfile) { isLoading = false }
        } else {
            onDismissRequest()
        }
    }

    FullScreenDialog(onDismissRequest) {
        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            TopBarForBack(onBack = onDismissRequest)
            IEUMSpacer(size = 12)
            UserGuideArea(
                modifier = Modifier.padding(horizontal = screenPadding),
                guide = stringResource(R.string.guide_select_residence),
            )
            IEUMSpacer(size = 40)
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                AddressComponent(state = state)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = screenPadding)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    PatchLockCheckBox(
                        isLocked = isOpened.not(),
                        onClick = { isOpened = isOpened.not() },
                    )
                    Lime400Button(
                        text = stringResource(R.string.complete),
                        enabled = isLoading.not(),
                        onClick = ::patchResidence,
                    )
                }
            }
        }
    }
}