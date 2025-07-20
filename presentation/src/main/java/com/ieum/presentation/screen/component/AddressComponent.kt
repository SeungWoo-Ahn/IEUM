package com.ieum.presentation.screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ieum.design_system.selector.CitySelector
import com.ieum.design_system.selector.ProvinceSelector
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.AddressUiState

@Composable
fun AddressComponent(
    modifier: Modifier = Modifier,
    state: AddressState
) {
    when (val uiState = state.uiState) {
        AddressUiState.Loading -> {
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
                    modifier = Modifier.weight(1f)
                ) {
                   items(
                       items = uiState.cityList,
                       key = { it.code }
                   ) { city ->
                       CitySelector(
                           isSelected = city == uiState.selectedCity,
                           name = city.name,
                           onClick = { state.selectCity(city, uiState) }
                       )
                   }
                }
                LazyColumn(
                    modifier = Modifier.weight(1.4f)
                ) {
                    items(
                        items = uiState.provinceList,
                        key = { it.code }
                    ) { province ->
                        ProvinceSelector(
                            isSelected = province == uiState.selectedProvince,
                            name = province.name,
                            onClick = { state.selectProvince(province, uiState) }
                        )
                    }
                }
            }
        }
    }
}