package com.ieum.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate300
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.presentation.R
import com.ieum.presentation.model.user.OthersProfileUiModel
import com.ieum.presentation.screen.main.othersProfile.OthersProfileTab

@Composable
private fun ProfileTabItem(
    name: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier.noRippleClickable(onClick = onClick),
        text = name,
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 20.sp,
        color = if (selected) Slate900 else Slate300,
        textDecoration = if (selected) TextDecoration.Underline else TextDecoration.None,
    )
}

@Composable
private fun ProfileBox(
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = White,
                shape = MaterialTheme.shapes.medium,
            )
            .border(
                width = 1.dp,
                color = Slate200,
                shape = MaterialTheme.shapes.medium,
            ),
        content = content,
    )
}

@Composable
private fun ProfileDivider() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 1.dp,
        color = Slate200,
    )
}

@Composable
private fun ProfileTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
private fun ProfileChip(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = Slate100,
                shape = RoundedCornerShape(size = 30.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
fun OthersProfileTabArea(
    modifier: Modifier = Modifier,
    currentTab: OthersProfileTab,
    onTabClick: (OthersProfileTab) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = screenPadding),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OthersProfileTab.entries.forEach { tab ->
            ProfileTabItem(
                name = stringResource(tab.displayName),
                selected = tab == currentTab,
                onClick = { onTabClick(tab) }
            )
        }
    }
}

@Composable
fun OthersProfileSection(
    modifier: Modifier = Modifier,
    profile: OthersProfileUiModel,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
            .padding(
                horizontal = screenPadding,
                vertical = 16.dp,
            ),
    ) {
        ProfileBox {
            if (profile.openedDataEmpty) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.opened_profile_data_empty),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            } else {
                profile.diagnoses?.let {
                    OthersProfileItem(
                        title = stringResource(R.string.diagnose)
                    ) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            it.forEach { data -> ProfileChip(text = data) }
                        }
                    }
                }
                profile.chemotherapy?.let {
                    OthersProfileItem(
                        title = stringResource(R.string.chemotherapy)
                    ) {
                        ProfileChip(text = it)
                    }
                }
                profile.radiationTherapy?.let {
                    OthersProfileItem(
                        title = stringResource(R.string.radiationTherapy)
                    ) {
                        ProfileChip(text = it)
                    }
                }
                profile.ageGroup?.let {
                    OthersProfileItem(
                        title = stringResource(R.string.age_group)
                    ) {
                        ProfileChip(text = stringResource(it.description))
                    }
                }
                profile.residenceArea?.let {
                    OthersProfileItem(
                        title = stringResource(R.string.residence_area)
                    ) {
                        ProfileChip(text = it)
                    }
                }
                profile.hospitalArea?.let {
                    OthersProfileItem(
                        title = stringResource(R.string.hospital_area),
                        needDivider = false
                    ) {
                        ProfileChip(text = it)
                    }
                }
            }
        }
    }
}

@Composable
private fun OthersProfileItem(
    modifier: Modifier = Modifier,
    title: String,
    needDivider: Boolean = true,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            ProfileTitle(text = title)
            content()
        }
        if (needDivider) {
            ProfileDivider()
        }
    }
}