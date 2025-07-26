package com.ieum.design_system.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.ieum.design_system.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IEUMBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit),
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = White,
        dragHandle = null,
        contentWindowInsets = {
            WindowInsets(
                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            )
        },
        content = content,
    )
}