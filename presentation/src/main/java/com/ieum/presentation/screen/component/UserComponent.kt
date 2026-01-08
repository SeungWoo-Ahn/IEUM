package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.IEUMButton
import com.ieum.design_system.button.Lime400Button
import com.ieum.design_system.icon.InfoCircleIcon
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.design_system.theme.Gray500
import com.ieum.design_system.theme.Gray600
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White
import com.ieum.design_system.util.dropShadow
import com.ieum.presentation.R
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel
import com.ieum.presentation.state.DiagnoseState

@Composable
fun UserGuideArea(
    modifier: Modifier = Modifier,
    guide: String,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = guide,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
fun UserSelector(
    modifier: Modifier = Modifier,
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier.dropShadow(),
        text = name,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Slate900 else White,
            contentColor = if (isSelected) White else Slate900,
        ),
        onClick = onClick
    )
}

@Composable
fun SelectUserType(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<UserTypeUiModel>,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.itemList.forEach { userType ->
            UserSelector(
                name = stringResource(userType.description),
                isSelected = userType == UserTypeUiModel.CAREGIVER,
                onClick = {
                    state.selectItem(userType)
                    onClick()
                }
            )
        }
    }
}

@Composable
fun SelectSex(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<SexUiModel>,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.itemList.forEach { sex ->
            UserSelector(
                name = stringResource(sex.description),
                isSelected = sex == SexUiModel.FEMALE,
                onClick = {
                    state.selectItem(sex)
                    onClick()
                },
            )
        }
    }
}

@Composable
fun NicknameInfo() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InfoCircleIcon()
        Text(
            text = stringResource(R.string.info_nickname),
            style = MaterialTheme.typography.bodyMedium,
            color = Gray600,
        )
    }
}

@Composable
fun SelectedCountButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    selectedCount: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$selectedCount",
                style = MaterialTheme.typography.labelMedium,
                color = Lime500,
            )
            Text(
                text = stringResource(R.string.selected_n_completed),
                style = MaterialTheme.typography.labelMedium,
                color = Gray500,
            )
        }
        Lime400Button(
            text = text,
            enabled = enabled,
            onClick = onClick,
        )
    }
}

@Composable
fun SelectDiagnose(
    modifier: Modifier = Modifier,
    state: DiagnoseState,
    showCancerStageSheet: (CancerDiagnoseUiModel) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        state.cancerDiagnoseState.itemList.forEach { diagnose ->
            UserSelector(
                name = diagnose.key.displayName + when (diagnose.stage) {
                    CancerStageUiModel.STAGE_UNKNOWN -> ""
                    else -> diagnose.stage.description
                },
                isSelected = diagnose.isSelected,
                onClick = { showCancerStageSheet(diagnose) }
            )
        }
        state.commonDiagnoseState.itemList.forEach { diagnose ->
            UserSelector(
                name = diagnose.displayName,
                isSelected = state.commonDiagnoseState.isSelected(diagnose),
                onClick = { state.commonDiagnoseState.selectItem(diagnose) }
            )
        }
    }
}

@Composable
fun SelectAgeGroup(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<AgeGroupUiModel>,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.itemList.forEach { ageGroup ->
            UserSelector(
                name = stringResource(ageGroup.description),
                isSelected = state.isSelected(ageGroup),
                onClick = { state.selectItem(ageGroup) }
            )
        }
    }
}