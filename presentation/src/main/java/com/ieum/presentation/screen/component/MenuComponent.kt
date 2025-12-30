package com.ieum.presentation.screen.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.MenuIcon
import com.ieum.design_system.theme.White
import com.ieum.presentation.R

enum class DropDownMenu(@StringRes val displayName: Int) {
    REPORT(R.string.report),
    DELETE(R.string.delete),
}

@Composable
fun IEUMDropDownMenu(
    isMine: Boolean,
    onMenu: (DropDownMenu) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val menus = if (isMine) listOf(DropDownMenu.DELETE) else listOf(DropDownMenu.REPORT)

    Box {
        IconButton(onClick = { expanded = expanded.not() }) {
            MenuIcon()
        }
        DropdownMenu(
            modifier = Modifier.background(color = White),
            expanded = expanded,
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = { expanded = false }
        ) {
            menus.forEach { menu ->
                DropDownMenuItem(
                    text = stringResource(menu.displayName),
                    onClick = { onMenu(menu) }
                )
            }
        }
    }
}

@Composable
private fun DropDownMenuItem(
    text: String,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
            )
        },
        onClick = onClick
    )
}