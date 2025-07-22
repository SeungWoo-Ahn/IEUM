package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ieum.design_system.button.SkipOrNextButton
import com.ieum.design_system.selector.CitySelector
import com.ieum.design_system.selector.ProvinceSelector
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.AddressUiState

@Composable
fun AddressComponent(
    modifier: Modifier = Modifier,
    nextEnabled: Boolean,
    state: AddressState,
    onNextStep: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AddressList(state = state)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
        ) {
            SkipOrNextButton(
                enabled = nextEnabled,
                onNext = onNextStep,
            )
        }
    }
}

@Composable
private fun AddressList(
    modifier: Modifier = Modifier,
    state: AddressState,
) {
    when (val uiState = state.uiState) {
        AddressUiState.Loading -> {
            LaunchedEffect(Unit) {
                state.getCityList()
            }
            // TODO: 로딩 컴포넌트
        }
        AddressUiState.Error -> {
            // TODO: 재시도 컴포넌트
        }
        is AddressUiState.Success -> {
            Row(
                modifier = modifier.fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = state.cityListScrollState,
                ) {
                    items(
                        items = uiState.cityList,
                        key = { it.code }
                    ) { city ->
                        CitySelector(
                            isSelected = city == uiState.selectedCity,
                            name = city.name,
                            onClick = { state.selectCity(city) }
                        )
                    }
                    item {
                        IEUMSpacer(size = 120)
                    }
                }
                LazyColumn(
                    modifier = Modifier.weight(1.4f),
                    state = state.provinceListScrollState,
                ) {
                    items(
                        items = uiState.provinceList,
                        key = { it.code }
                    ) { province ->
                        ProvinceSelector(
                            isSelected = province == uiState.selectedProvince,
                            name = province.name,
                            onClick = { state.selectProvince(province) }
                        )
                    }
                    item {
                        IEUMSpacer(size = 120)
                    }
                }
            }
        }
    }
}