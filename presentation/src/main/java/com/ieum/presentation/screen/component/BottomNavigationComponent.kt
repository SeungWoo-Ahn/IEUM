package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ieum.design_system.icon.CalendarDarkIcon
import com.ieum.design_system.icon.CalendarLightIcon
import com.ieum.design_system.icon.FeedDarkIcon
import com.ieum.design_system.icon.FeedLightIcon
import com.ieum.design_system.icon.ProfileDarkIcon
import com.ieum.design_system.icon.ProfileLightIcon
import com.ieum.design_system.theme.White
import com.ieum.design_system.util.dropShadow
import com.ieum.design_system.util.noRippleClickable

enum class BottomNavigationItem(
    val unselectedIcon: @Composable () -> Unit,
    val selectedIcon: @Composable () -> Unit,
) {
    Feed(
        unselectedIcon = { FeedLightIcon() },
        selectedIcon = { FeedDarkIcon() }
    ),
    Calendar(
        unselectedIcon = { CalendarLightIcon() },
        selectedIcon = { CalendarDarkIcon() }
    ),
    Profile(
        unselectedIcon = { ProfileLightIcon() },
        selectedIcon = { ProfileDarkIcon() },
    )
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavigationItem,
    onItemClick: (BottomNavigationItem) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = MaterialTheme.shapes.medium,
            )
            .dropShadow(
                offsetY = (-4).dp
            )
            .padding(
                horizontal = 24.dp,
                vertical = 14.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BottomNavigationItem.entries.forEach { item ->
            Box(
                modifier = Modifier.noRippleClickable { onItemClick(item) }
            ) {
                if (item == selectedItem) {
                    item.selectedIcon()
                } else {
                    item.unselectedIcon()
                }
            }
        }
    }
}