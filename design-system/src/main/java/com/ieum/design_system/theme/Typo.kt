package com.ieum.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ieum.design_system.R

private val Pretendard = FontFamily(
    Font(R.font.pretendard_medium),
    Font(R.font.pretendard_semibold),
    Font(R.font.pretendard_bold),
)

internal val IEUMTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 28.sp * 1.3f,
        letterSpacing = (-0.02).em,
        color = Gray950,
    ),
    headlineSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 17.sp * 1.3f,
        letterSpacing = (-0.02).em,
        color = Gray950,
    ),
    titleLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = (-0.01).em,
        color = Gray950,
    ),
    titleMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        letterSpacing = (-0.01).em,
        color = Gray950,
    ),
    titleSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        letterSpacing = (-0.01).em,
        color = Gray950,
    ),
    bodyMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 17.sp * 1.4f,
        letterSpacing = (-0.01).em,
        color = Gray950,
    ),
    bodySmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 15.sp * 1.4f,
        letterSpacing = (-0.01).em,
        color = Gray950,
    ),
    labelMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = Gray950,
    )
)