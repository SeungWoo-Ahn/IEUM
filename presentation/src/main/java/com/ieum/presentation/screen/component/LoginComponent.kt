package com.ieum.presentation.screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.GoogleIcon
import com.ieum.design_system.icon.KakaoIcon
import com.ieum.design_system.theme.Gray950
import com.ieum.design_system.theme.KakaoYellow
import com.ieum.design_system.theme.White
import com.ieum.presentation.R

@Composable
fun KakaoLoginButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = KakaoYellow,
            contentColor = Gray950,
            disabledContainerColor = KakaoYellow,
            disabledContentColor = Gray950,
        ),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = 12.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            KakaoIcon(
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.login_with_kakao),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = White,
            contentColor = Gray950,
            disabledContainerColor = White,
            disabledContentColor = Gray950,
        ),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFF747775)
        ),
        contentPadding = PaddingValues(horizontal = 12.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            GoogleIcon(
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.login_with_google),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}