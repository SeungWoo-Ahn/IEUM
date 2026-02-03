package com.ieum.presentation.screen.auth.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.presentation.R
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel
import com.ieum.presentation.screen.component.CancerStageSheet
import com.ieum.presentation.screen.component.RegisterPolicySheet
import com.ieum.presentation.screen.component.RegisterSelectAddress
import com.ieum.presentation.screen.component.RegisterSelectAgeGroup
import com.ieum.presentation.screen.component.RegisterSelectDiagnose
import com.ieum.presentation.screen.component.RegisterSelectSex
import com.ieum.presentation.screen.component.RegisterSelectUserType
import com.ieum.presentation.screen.component.RegisterTypeInterest
import com.ieum.presentation.screen.component.RegisterTypeNickname
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.DiagnoseState
import kotlinx.coroutines.CoroutineScope

@Composable
fun RegisterRoute(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    moveWelcome: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val sheetState = viewModel.sheetState
    val buttonEnabled by remember { derivedStateOf { viewModel.nextEnabled() } }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                RegisterEvent.MoveWelcome -> moveWelcome()
                RegisterEvent.MoveBack -> onBack()
            }
        }
    }

    RegisterScreen(
        modifier = modifier,
        isLoading = uiState == RegisterUiState.Loading,
        buttonEnabled = buttonEnabled,
        currentStage = viewModel.currentStage,
        userTypeState = viewModel.userTypeState,
        sexState = viewModel.sexState,
        nickNameState = viewModel.nickNameState,
        diagnoseState = viewModel.diagnoseState,
        ageGroupState = viewModel.ageGroupState,
        residenceState = viewModel.residenceState,
        hospitalState = viewModel.hospitalState,
        interestState = viewModel.interestState,
        showCancerStageSheet = viewModel::showCancerStageSheet,
        onPrevStep = viewModel::onPrevStep,
        onNextStep = viewModel::onNextStep,
    )
    if (sheetState is RegisterSheetState.ShowCancerStageSheet) {
        CancerStageSheet(
            scope = scope,
            data = sheetState.data,
            callback = sheetState.callback,
            onDismissRequest = viewModel::dismiss,
        )
    }
    if (sheetState is RegisterSheetState.ShowPolicySheet) {
        RegisterPolicySheet(
            scope = scope,
            buttonEnabled = uiState != RegisterUiState.Loading,
            onRegister = viewModel::register,
            onDismissRequest = viewModel::dismiss
        )
    }
    BackHandler(onBack = viewModel::onPrevStep)
}

@Composable
private fun RegisterScreen(
    modifier: Modifier,
    isLoading: Boolean,
    buttonEnabled: Boolean,
    currentStage: RegisterStage,
    userTypeState: ISingleSelectorState<UserTypeUiModel>,
    sexState: ISingleSelectorState<SexUiModel>,
    nickNameState: IMaxLengthTextFieldState,
    diagnoseState: DiagnoseState,
    ageGroupState: ISingleSelectorState<AgeGroupUiModel>,
    residenceState: AddressState,
    hospitalState: AddressState,
    interestState: IMaxLengthTextFieldState,
    showCancerStageSheet: (CancerDiagnoseUiModel) -> Unit,
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopBarForBack(onBack = onPrevStep)
        IEUMSpacer(size = 12)
        when (currentStage) {
            RegisterStage.SelectUserType -> RegisterSelectUserType(
                state = userTypeState,
                onClick = onNextStep,
            )
            RegisterStage.SelectSex -> RegisterSelectSex(
                state = sexState,
                onClick = onNextStep,
            )
            RegisterStage.TypeNickname -> RegisterTypeNickname(
                buttonEnabled = buttonEnabled,
                state = nickNameState,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectDiagnose -> RegisterSelectDiagnose(
                state = diagnoseState,
                buttonEnabled = buttonEnabled,
                showCancerStageSheet = showCancerStageSheet,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectAgeGroup -> RegisterSelectAgeGroup(
                state = ageGroupState,
                buttonEnabled = buttonEnabled,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectResidence -> RegisterSelectAddress(
                guide = stringResource(R.string.guide_select_residence),
                state = residenceState,
                buttonEnabled = buttonEnabled,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectHospital -> RegisterSelectAddress(
                guide = stringResource(R.string.guide_select_hospital),
                state = hospitalState,
                buttonEnabled = buttonEnabled,
                onButtonClick = onNextStep,
            )
            RegisterStage.TypeInterest -> RegisterTypeInterest(
                state = interestState,
                skipEnabled = isLoading.not(),
                buttonEnabled = buttonEnabled,
                onButtonClick = onNextStep,
            )
        }
    }
}
