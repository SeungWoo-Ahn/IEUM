package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ieum.design_system.progressbar.IEUMLoadingComponent
import com.ieum.design_system.spacer.IEUMSpacer
import com.ieum.design_system.theme.Gray200
import com.ieum.design_system.theme.Gray50
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate300
import com.ieum.design_system.theme.White
import com.ieum.design_system.util.topBorder
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.AddressUiState

@Composable
fun AddressComponent(
    modifier: Modifier = Modifier,
    state: AddressState,
) {
    when (val uiState = state.uiState) {
        AddressUiState.Loading -> {
            LaunchedEffect(Unit) {
                state.getCityList()
            }
            IEUMLoadingComponent(
                modifier = Modifier.padding(bottom = 120.dp)
            )
        }
        AddressUiState.Error -> ErrorComponent(
            modifier = Modifier.padding(bottom = 120.dp),
            onRetry = state::getCityList
        )
        is AddressUiState.Success -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = White)
                    .topBorder(
                        height = 1f,
                        color = Gray200,
                    )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .background(color = Gray50),
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
                VerticalDivider(
                    thickness = 1.dp,
                    color = Gray200,
                )
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

@Composable
private fun CitySelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    name: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) Slate300 else Gray50,
            )
            .clickable(onClick = onClick)
            .padding(all = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ProvinceSelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    name: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) Slate100 else White,
            )
            .clickable(onClick = onClick)
            .padding(all = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}