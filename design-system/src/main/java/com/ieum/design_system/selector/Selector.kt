package com.ieum.design_system.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CitySelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    name: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) {
                    Color.Gray
                } else {
                    Color.LightGray
                }
            )
            .clickable(onClick = onClick)
            .padding(all = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
        )
    }
}

@Preview
@Composable
internal fun CitySelectorPreview() {
    var isSelected by remember { mutableStateOf(false) }
    CitySelector(
        isSelected = isSelected,
        name = "서울특별시",
        onClick = { isSelected = isSelected.not() },
    )
}

@Composable
fun ProvinceSelector(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    name: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) {
                    Color.Gray
                } else {
                    Color.LightGray
                }
            )
            .clickable(onClick = onClick)
            .padding(all = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
        )
    }
}

@Preview
@Composable
internal fun ProvinceSelectorPreview() {
    var isSelected by remember { mutableStateOf(false) }
    ProvinceSelector(
        isSelected = isSelected,
        name = "강남구",
        onClick = { isSelected = isSelected.not() },

    )
}