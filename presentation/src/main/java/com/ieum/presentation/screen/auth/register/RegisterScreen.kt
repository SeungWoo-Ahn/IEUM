package com.ieum.presentation.screen.auth.register

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.button.BlackButton
import com.ieum.design_system.button.WhiteButton
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.design_system.textfield.IMaxLengthTextFieldState
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.mapper.toDescription

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
        ageGroupState = viewModel.ageGroupState,
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
    ageGroupState: ISingleSelectorState<AgeGroup>,
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
        // 각 Stage 영역
        when (currentStage) {
            RegisterStage.SelectUserType -> SelectUserType(
                userTypeState = userTypeState,
                onNextStep = onNextStep
            )
            RegisterStage.TypeNickname -> {}
            RegisterStage.SelectAgeGroup -> {}
            RegisterStage.TypeInterest -> {}
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