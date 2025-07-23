package com.ieum.presentation.screen.auth.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.NextButton
import com.ieum.design_system.button.SelectedCountButton
import com.ieum.design_system.button.SkipOrNextButton
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.topbar.TopBarForBack
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.screen.component.AddressComponent
import com.ieum.presentation.screen.component.DiagnoseComponent
import com.ieum.presentation.screen.component.SelectAgeGroup
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
    val nextEnabled by remember { derivedStateOf { viewModel.nextEnabled() } }

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
        scope = scope,
        nextEnabled = nextEnabled,
        currentStage = viewModel.currentStage,
        userTypeState = viewModel.userTypeState,
        nickNameState = viewModel.nickNameState,
        diagnoseState = viewModel.diagnoseState,
        ageGroupState = viewModel.ageGroupState,
        residenceState = viewModel.residenceState,
        hospitalState = viewModel.hospitalState,
        interestState = viewModel.interestState,
        onPrevStep = viewModel::onPrevStep,
        onNextStep = viewModel::onNextStep,
    )
    BackHandler(onBack = viewModel::onPrevStep)
}

@Composable
private fun RegisterScreen(
    modifier: Modifier,
    scope: CoroutineScope,
    nextEnabled: Boolean,
    currentStage: RegisterStage,
    userTypeState: ISingleSelectorState<UserType>,
    nickNameState: IMaxLengthTextFieldState,
    diagnoseState: DiagnoseState,
    ageGroupState: ISingleSelectorState<AgeGroup>,
    residenceState: AddressState,
    hospitalState: AddressState,
    interestState: IMaxLengthTextFieldState,
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopBarForBack(onBack = onPrevStep)
        RegisterGuideArea(guide = stringResource(currentStage.guide))
        Box(
            modifier = Modifier.weight(1f)
        ) {
            RegisterStageScreenArea(
                scope = scope,
                currentStage = currentStage,
                userTypeState = userTypeState,
                nickNameState = nickNameState,
                diagnoseState = diagnoseState,
                ageGroupState = ageGroupState,
                residenceState = residenceState,
                hospitalState = hospitalState,
                interestState = interestState,
                onNextStep = onNextStep,
            )
            RegisterButtonArea(
                modifier = Modifier.align(Alignment.BottomCenter),
                currentStage = currentStage,
                nextEnabled = nextEnabled,
                selectedDiagnoseCnt = diagnoseState.getSelectedCnt(),
                onNextStep = onNextStep,
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
            .padding(
                top = 12.dp,
                bottom = 48.dp,
                start = 24.dp,
                end = 24.dp,
            )
    ) {
        Text(
            text = guide,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RegisterStageScreenArea(
    scope: CoroutineScope,
    currentStage: RegisterStage,
    userTypeState: ISingleSelectorState<UserType>,
    nickNameState: IMaxLengthTextFieldState,
    diagnoseState: DiagnoseState,
    ageGroupState: ISingleSelectorState<AgeGroup>,
    residenceState: AddressState,
    hospitalState: AddressState,
    interestState: IMaxLengthTextFieldState,
    onNextStep: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = if (currentStage.needFullScreen()) 0.dp else 24.dp
            )
    ) {
        when (currentStage) {
            RegisterStage.SelectUserType -> SelectUserType(state = userTypeState, onClick = onNextStep)
            RegisterStage.TypeNickname -> TypeNickname(state = nickNameState)
            RegisterStage.SelectDiagnose -> DiagnoseComponent(scope = scope, state = diagnoseState)
            RegisterStage.SelectAgeGroup -> SelectAgeGroup(state = ageGroupState)
            RegisterStage.SelectResidence -> AddressComponent(state = residenceState)
            RegisterStage.SelectHospital -> AddressComponent(state = hospitalState)
            RegisterStage.TypeInterest -> TypeInterest(state = interestState)
        }
    }
}

@Composable
private fun RegisterButtonArea(
    modifier: Modifier = Modifier,
    currentStage: RegisterStage,
    nextEnabled: Boolean,
    selectedDiagnoseCnt: Int,
    onNextStep: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp,
            ),
        contentAlignment = Alignment.Center
    ) {
        when (currentStage) {
            RegisterStage.SelectUserType -> {/*없음*/}
            RegisterStage.TypeNickname -> NextButton(enabled = nextEnabled, onClick = onNextStep)
            RegisterStage.SelectDiagnose -> SelectedCountButton(
                enabled = nextEnabled,
                selectedCount = selectedDiagnoseCnt,
                onClick = onNextStep
            )
            else -> SkipOrNextButton(enabled = nextEnabled, onNext = onNextStep)
        }
    }
}
