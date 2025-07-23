package com.ieum.presentation.screen.auth.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.BlackButton
import com.ieum.design_system.button.NextButton
import com.ieum.design_system.button.SkipOrNextButton
import com.ieum.design_system.button.WhiteButton
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.design_system.textfield.MaxLengthTextField
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.R
import com.ieum.presentation.mapper.toDescription
import com.ieum.presentation.screen.component.AddressComponent
import com.ieum.presentation.screen.component.DiagnoseComponent
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.DiagnoseState

@Composable
fun RegisterRoute(
    modifier: Modifier = Modifier,
    moveWelcome: () -> Unit,
    onBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val nextEnabled by remember { derivedStateOf { viewModel.nextEnabled() } }

    RegisterScreen(
        modifier = modifier,
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
        // AppBar 영역
        // Guide 영역
        RegisterGuide(guide = stringResource(currentStage.guide))
        when (currentStage) {
            RegisterStage.SelectUserType -> SelectUserType(
                userTypeState = userTypeState,
                onNextStep = onNextStep
            )
            RegisterStage.TypeNickname -> TypeNickname(
                nextEnabled = nextEnabled,
                nickNameState = nickNameState,
                onNextStep = onNextStep
            )
            RegisterStage.SelectDiagnose -> DiagnoseComponent(
                nextEnabled = nextEnabled,
                diagnoseState = diagnoseState,
                onNextStep = onNextStep,
            )
            RegisterStage.SelectAgeGroup -> SelectAgeGroup(
                nextEnabled = nextEnabled,
                ageGroupState = ageGroupState,
                onNextStep = onNextStep,
            )
            RegisterStage.SelectResidence -> AddressComponent(
                nextEnabled = nextEnabled,
                state = residenceState,
                onNextStep = onNextStep,
            )
            RegisterStage.SelectHospital -> AddressComponent(
                nextEnabled = nextEnabled,
                state = hospitalState,
                onNextStep = onNextStep,
            )
            RegisterStage.TypeInterest -> TypeInterest(
                nextEnabled = nextEnabled,
                interestState = interestState,
                onNextStep = onNextStep,
            )
        }
    }
}

@Composable
private fun RegisterGuide(
    modifier: Modifier = Modifier,
    guide: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 60.dp,
                bottom = 48.dp
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
private fun SelectUserType(
    modifier: Modifier = Modifier,
    userTypeState: ISingleSelectorState<UserType>,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
       userTypeState.itemList.forEach { userType ->
           when (userType) {
               UserType.PATIENT -> WhiteButton(
                   text = stringResource(userType.toDescription()),
                   onClick = {
                       userTypeState.selectItem(userType)
                       onNextStep()
                   }
               )
               UserType.CAREGIVER -> BlackButton(
                   text = stringResource(userType.toDescription()),
                   onClick = {
                       userTypeState.selectItem(userType)
                       onNextStep()
                   }
               )
           }
       }
    }
}

@Composable
private fun TypeNickname(
    modifier: Modifier = Modifier,
    nextEnabled: Boolean,
    nickNameState: IMaxLengthTextFieldState,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    ) {
        MaxLengthTextField(
            state = nickNameState,
            placeHolder = stringResource(R.string.placeholder_type_nickname)
        )
        IEUMSpacer(
            modifier = Modifier.weight(1f)
        )
        NextButton(
            enabled = nextEnabled,
            onClick = onNextStep,
        )
    }
}

@Composable
private fun SelectAgeGroup(
    modifier: Modifier = Modifier,
    nextEnabled: Boolean,
    ageGroupState: ISingleSelectorState<AgeGroup>,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    ) {
        ageGroupState.itemList.forEach { ageGroup ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(
                        color = if (ageGroupState.isSelected(ageGroup)) {
                            Color.Black
                        } else {
                            Color.White
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { ageGroupState.selectItem(ageGroup) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(ageGroup.toDescription()),
                    color = if (ageGroupState.isSelected(ageGroup)) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )
            }
            IEUMSpacer(size = 12)
        }
        IEUMSpacer(
            modifier = Modifier.weight(1f)
        )
        SkipOrNextButton(
            enabled = nextEnabled,
            onNext = onNextStep,
        )
    }
}

@Composable
fun TypeInterest(
    modifier: Modifier = Modifier,
    nextEnabled: Boolean,
    interestState: IMaxLengthTextFieldState,
    onNextStep: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    ) {
        MaxLengthTextField(
            state = interestState,
            placeHolder = stringResource(R.string.placeholder_type_interest)
        )
        IEUMSpacer(
            modifier = Modifier.weight(1f)
        )
        SkipOrNextButton(
            enabled = nextEnabled,
            onNext = onNextStep,
        )
    }
}