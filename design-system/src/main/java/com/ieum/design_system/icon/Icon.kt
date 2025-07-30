package com.ieum.design_system.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.R

@Composable
fun IEUMIcon() {
    Icon(
        modifier = Modifier.size(
            width = 50.dp,
            height = 26.dp,
        ),
        painter = painterResource(R.drawable.ic_ieum),
        tint = Color.Unspecified,
        contentDescription = "ic-logo",
    )
}

@Composable
fun BackIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_back),
        contentDescription = "ic-back",
    )
}

@Composable
fun InfoCircleIcon() {
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.ic_info_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-info-circle",
    )
}

@Composable
fun CheckCircleIcon() {
    Icon(
        modifier = Modifier.size(88.dp),
        painter = painterResource(R.drawable.ic_check_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-check-circle",
    )
}

@Composable
fun CloseIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_close),
        tint = Color.Unspecified,
        contentDescription = "ic-close",
    )
}

@Composable
fun TreatmentRecordsIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_treatment_records),
        tint = Color.Unspecified,
        contentDescription = "ic-treatment-records",
    )
}

@Composable
fun DailyRecordsIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_daily_records),
        tint = Color.Unspecified,
        contentDescription = "ic-daily-records",
    )
}

@Composable
fun ThunderIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_thunder),
        tint = Color.Unspecified,
        contentDescription = "ic-thunder",
    )
}

@Composable
fun MedicineIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_medicine),
        tint = Color.Unspecified,
        contentDescription = "ic-medicine",
    )
}

@Composable
fun MealIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_meal),
        tint = Color.Unspecified,
        contentDescription = "ic-meal",
    )
}

@Composable
fun MemoIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_memo),
        tint = Color.Unspecified,
        contentDescription = "ic-memo",
    )
}

@Composable
fun ImageIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_image),
        tint = Color.Unspecified,
        contentDescription = "ic-image",
    )
}

@Composable
fun CommunityIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_community),
        tint = Color.Unspecified,
        contentDescription = "ic-community",
    )
}

@Composable
fun CompleteIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_complete),
        tint = Color.Unspecified,
        contentDescription = "ic-complete",
    )
}

@Composable
fun IncompleteIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_incomplete),
        tint = Color.Unspecified,
        contentDescription = "ic-incomplete",
    )
}