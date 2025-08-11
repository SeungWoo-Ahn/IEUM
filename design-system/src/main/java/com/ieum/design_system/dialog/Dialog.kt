package com.ieum.design_system.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding

@Composable
fun IEUMDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding),
            color = White,
            shape = MaterialTheme.shapes.medium,
            content = content,
        )
    }
}