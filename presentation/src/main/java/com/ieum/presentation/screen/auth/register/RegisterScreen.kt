package com.ieum.presentation.screen.auth.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel
import com.ieum.presentation.screen.component.CancerStageSheet
import com.ieum.presentation.screen.component.SelectAddress
import com.ieum.presentation.screen.component.SelectAgeGroup
import com.ieum.presentation.screen.component.SelectDiagnose
import com.ieum.presentation.screen.component.SelectSex
import com.ieum.presentation.screen.component.SelectUserType
import com.ieum.presentation.screen.component.TypeInterest
import com.ieum.presentation.screen.component.TypeNickname
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
    val buttonEnabled by remember { derivedStateOf { viewModel.nextEnabled() && uiState != RegisterUiState.Loading } }

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
    if (uiState is RegisterUiState.ShowCancerStageSheet) {
        CancerStageSheet(
            scope = scope,
            data = uiState.data,
            callback = uiState.callback,
            onDismissRequest = viewModel::resetUiState,
        )
    }
    BackHandler(onBack = viewModel::onPrevStep)
}

@Composable
private fun RegisterScreen(
    modifier: Modifier,
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
        RegisterGuideArea(guide = stringResource(currentStage.guide))
        when (currentStage) {
            RegisterStage.SelectUserType -> SelectUserType(
                state = userTypeState,
                onClick = onNextStep,
            )
            RegisterStage.SelectSex -> SelectSex(
                state = sexState,
                onClick = onNextStep,
            )
            RegisterStage.TypeNickname -> TypeNickname(
                buttonEnabled = buttonEnabled,
                state = nickNameState,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectDiagnose -> SelectDiagnose(
                buttonEnabled = buttonEnabled,
                state = diagnoseState,
                showCancerStageSheet = showCancerStageSheet,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectAgeGroup -> SelectAgeGroup(
                buttonEnabled = buttonEnabled,
                state = ageGroupState,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectResidence -> SelectAddress(
                buttonEnabled = buttonEnabled,
                state = residenceState,
                onButtonClick = onNextStep,
            )
            RegisterStage.SelectHospital -> SelectAddress(
                buttonEnabled = buttonEnabled,
                state = hospitalState,
                onButtonClick = onNextStep,
            )
            RegisterStage.TypeInterest -> TypeInterest(
                buttonEnabled = buttonEnabled,
                state = interestState,
                onButtonClick = onNextStep,
            )
        }
    }
}

@Composable
private fun RegisterGuideArea(
    modifier: Modifier = Modifier,
    guide: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding)
    ) {
        Text(
            text = guide,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}
