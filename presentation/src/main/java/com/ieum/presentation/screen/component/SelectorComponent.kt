package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.IEUMButton
import com.ieum.design_system.selector.ISingleSelectorState
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.UserType
import com.ieum.presentation.mapper.toDescription

@Composable
fun RegisterSelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    name: String,
    onClick: () -> Unit,
) {
    IEUMButton(
        modifier = modifier,
        text = name,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Black else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black,
        ),
        onClick = onClick
    )
}

@Composable
fun SelectUserType(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<UserType>,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.itemList.forEach { userType ->
            RegisterSelector(
                isSelected = userType == UserType.CAREGIVER,
                name = stringResource(userType.toDescription()),
                onClick = {
                    state.selectItem(userType)
                    onClick()
                }
            )
        }
    }
}

@Composable
fun SelectAgeGroup(
    modifier: Modifier = Modifier,
    state: ISingleSelectorState<AgeGroup>,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        state.itemList.forEach { ageGroup ->
            RegisterSelector(
                isSelected = state.isSelected(ageGroup),
                name = stringResource(ageGroup.toDescription()),
                onClick = { state.selectItem(ageGroup) }
            )
        }
    }
}