package com.ieum.design_system.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ieum.design_system.R

@Composable
fun LoginBackGroundImage() {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.login_background),
        contentScale = ContentScale.FillWidth,
        contentDescription = "login-background",
    )
}