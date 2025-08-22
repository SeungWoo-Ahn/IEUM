package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.IEUMButton
import com.ieum.design_system.button.SkipOrNextButton
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.dropShadow
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel
import com.ieum.presentation.state.AddressState

@Composable
internal fun RegisterSelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    name: String,
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
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
            .padding(horizontal = screenPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.itemList.forEach { userType ->
            RegisterSelector(
                isSelected = userType == UserTypeUiModel.CAREGIVER,
                name = stringResource(userType.description),
                onClick = {
                    state.selectItem(userType)
                    onClick()
                },
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
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
            .padding(horizontal = screenPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.itemList.forEach { sex ->
            RegisterSelector(
                isSelected = sex == SexUiModel.FEMALE,
                name = stringResource(sex.description),
                onClick = {
                    state.selectItem(sex)
                    onClick()
                },
            )
        }
    }
}

@Composable
fun SelectAgeGroup(
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean,
    state: ISingleSelectorState<AgeGroupUiModel>,
    onButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
            .padding(horizontal = screenPadding)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            state.itemList.forEach { ageGroup ->
                RegisterSelector(
                    isSelected = state.isSelected(ageGroup),
                    name = stringResource(ageGroup.description),
                    onClick = { state.selectItem(ageGroup) }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            SkipOrNextButton(
                enabled = buttonEnabled,
                onNext = onButtonClick,
            )
        }
    }
}

@Composable
fun AddressSelector(
    modifier: Modifier = Modifier,
    buttonEnabled: Boolean,
    state: AddressState,
    onButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
    ) {
        AddressComponent(state = state)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = screenPadding,
                    vertical = 16.dp
                ),
            contentAlignment = Alignment.Center,
        ) {
            SkipOrNextButton(
                enabled = buttonEnabled,
                onNext = onButtonClick,
            )
        }
    }
}