package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.Gray50Button
import com.ieum.design_system.button.Lime100Button
import com.ieum.design_system.button.Lime400Button
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.textfield.MaxLengthTextField
import com.ieum.design_system.theme.screenPadding
import com.ieum.presentation.R
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.DiagnoseState


@Composable
fun RegisterSelectUserType(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<UserTypeUiModel>,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        UserGuideArea(guide = stringResource(R.string.guide_select_user_type))
        SelectUserType(
            state = state,
            onClick = onClick,
        )
    }
}

@Composable
fun RegisterSelectSex(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<SexUiModel>,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding),
        verticalArrangement = Arrangement.spacedBy(40.dp),
    ) {
        UserGuideArea(guide = stringResource(R.string.guide_select_sex))
        SelectSex(
            state = state,
            onClick = onClick,
        )
    }
}

@Composable
fun RegisterTypeNickname(
    modifier: Modifier = Modifier,
    state: IMaxLengthTextFieldState,
    buttonEnabled: Boolean,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding)
            .padding(bottom = 16.dp),
    ) {
        UserGuideArea(guide = stringResource(R.string.guide_type_nickname))
        IEUMSpacer(size = 16)
        NicknameInfo()
        IEUMSpacer(size = 40)
        MaxLengthTextField(
            state = state,
            placeHolder = stringResource(R.string.placeholder_type_nickname)
        )
        IEUMSpacer(modifier = Modifier.weight(1f))
        Lime400Button(
            text = stringResource(R.string.next),
            enabled = buttonEnabled,
            onClick = onButtonClick,
        )
    }
}

@Composable
fun RegisterSelectDiagnose(
    modifier: Modifier = Modifier,
    state: DiagnoseState,
    buttonEnabled: Boolean,
    showCancerStageSheet: (CancerDiagnoseUiModel) -> Unit,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding)
            .padding(bottom = 16.dp),
    ) {
        UserGuideArea(guide = stringResource(R.string.guide_select_diagnose))
        IEUMSpacer(size = 40)
        SelectDiagnose(
            state = state,
            showCancerStageSheet = showCancerStageSheet,
        )
        IEUMSpacer(modifier = Modifier.weight(1f))
        SelectedCountButton(
            text = stringResource(R.string.next),
            enabled = buttonEnabled,
            selectedCount = state.totalSelectedCount,
            onClick = onButtonClick,
        )
    }
}

@Composable
private fun SkipOrNextButton(
    modifier: Modifier = Modifier,
    skipEnabled: Boolean = true,
    nextEnabled: Boolean,
    onNext: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (nextEnabled.not()) {
            Gray50Button(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.skip),
                enabled = skipEnabled,
                onClick = onNext,
            )
        }
        Lime100Button(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.next),
            enabled = nextEnabled,
            onClick = onNext
        )
    }
}

@Composable
fun RegisterSelectAgeGroup(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<AgeGroupUiModel>,
    buttonEnabled: Boolean,
    onButtonClick: () -> Unit,
) {
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
        SkipOrNextButton(
            nextEnabled = buttonEnabled,
            onNext = onButtonClick,
        )
    }
}

@Composable
fun RegisterSelectAddress(
    modifier: Modifier = Modifier,
    guide: String,
    state: AddressState,
    buttonEnabled: Boolean,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        UserGuideArea(
            modifier = Modifier.padding(horizontal = screenPadding),
            guide = guide,
        )
        Box(
            modifier = Modifier.weight(1f),
        ) {
            AddressComponent(state = state)
            SkipOrNextButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = screenPadding)
                    .padding(bottom = 16.dp),
                nextEnabled = buttonEnabled,
                onNext = onButtonClick,
            )
        }
    }
}

@Composable
fun RegisterTypeInterest(
    modifier: Modifier = Modifier,
    state: IMaxLengthTextFieldState,
    skipEnabled: Boolean,
    buttonEnabled: Boolean,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding)
            .padding(bottom = 16.dp),
    ) {
        UserGuideArea(guide = stringResource(R.string.guide_type_interest))
        IEUMSpacer(size = 40)
        MaxLengthTextField(
            state = state,
            placeHolder = stringResource(R.string.placeholder_type_interest)
        )
        IEUMSpacer(modifier = Modifier.weight(1f))
        SkipOrNextButton(
            skipEnabled = skipEnabled,
            nextEnabled = buttonEnabled,
            onNext = onButtonClick,
        )
    }
}