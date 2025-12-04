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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ieum.design_system.icon.LockIcon
import com.ieum.design_system.icon.RightIcon
import com.ieum.design_system.icon.SettingIcon
import com.ieum.design_system.theme.Lime500
import com.ieum.design_system.theme.Slate100
import com.ieum.design_system.theme.Slate200
import com.ieum.design_system.theme.Slate300
import com.ieum.design_system.theme.Slate400
import com.ieum.design_system.theme.Slate500
import com.ieum.design_system.theme.Slate900
import com.ieum.design_system.theme.White
import com.ieum.design_system.theme.screenPadding
import com.ieum.design_system.util.noRippleClickable
import com.ieum.domain.model.user.ProfileProperty
import com.ieum.presentation.R
import com.ieum.presentation.model.post.PostTypeUiModel
import com.ieum.presentation.model.user.MyProfileUiModel
import com.ieum.presentation.model.user.OthersProfileUiModel
import com.ieum.presentation.screen.main.home.myProfile.MyProfileTab
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
fun MyProfileTobBar(
    modifier: Modifier = Modifier,
    currentTab: MyProfileTab,
    onTabClick: (MyProfileTab) -> Unit,
    onSettingClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = screenPadding,
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyProfileTabArea(
            currentTab = currentTab,
            onTabClick = onTabClick,
        )
        Surface(
            shape = CircleShape,
            onClick = onSettingClick
        ) {
            SettingIcon()
        }
    }
}

@Composable
private fun MyProfileTabArea(
    currentTab: MyProfileTab,
    onTabClick: (MyProfileTab) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MyProfileTab.entries.forEach { tab ->
            ProfileTabItem(
                name = stringResource(tab.displayName),
                selected = tab == currentTab,
                onClick = { onTabClick(tab) }
            )
        }
    }
}

@Composable
fun MyProfilePostTypeArea(
    modifier: Modifier = Modifier,
    currentType: PostTypeUiModel,
    onPostType: (PostTypeUiModel) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = screenPadding,
                vertical = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PostTypeUiModel.entries.forEach { type ->
            PostTypeChip(
                postType = type,
                selected = type == currentType,
                onClick = { onPostType(type) }
            )
        }
    }
}

@Composable
private fun PostTypeChip(
    postType: PostTypeUiModel,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(
                color = White,
                shape = MaterialTheme.shapes.large,
            )
            .border(
                width = 1.dp,
                color = if (selected) Lime500 else White,
                shape = MaterialTheme.shapes.large,
            )
            .noRippleClickable(onClick = onClick)
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        postType.icon(20)
        Text(
            text = stringResource(postType.displayName),
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) Slate900 else Slate500,
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
                shape = MaterialTheme.shapes.large,
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
private fun ProfileChipList(data: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        data.forEach { text -> ProfileChip(text = text) }
    }
}

@Composable
fun MyProfileSection(
    modifier: Modifier = Modifier,
    profile: MyProfileUiModel,
    patchDiagnose: () -> Unit,
    patchSurgery: () -> Unit,
    patchChemotherapy: () -> Unit,
    patchRadiationTherapy: () -> Unit,
    patchAgeGroup: () -> Unit,
    patchResidenceArea: () -> Unit,
    patchHospitalArea: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
            .padding(all = screenPadding)
            .padding(bottom = 120.dp)
    ) {
        ProfileBox {
            MyProfileItem(
                title = stringResource(R.string.diagnose),
                profileProperty = profile.diagnoses,
                onClick = patchDiagnose,
            ) {
                ProfileChipList(it)
            }
            MyProfileItem(
                title = stringResource(R.string.surgery),
                profileProperty = profile.surgery,
                emptyDescription = stringResource(R.string.empty_surgery_description),
                onClick = patchSurgery,
            ) {
                ProfileChipList(it)
            }
            MyProfileItem(
                title = stringResource(R.string.chemotherapy),
                profileProperty = profile.chemotherapy,
                emptyDescription = stringResource(R.string.empty_chemotherapy_description),
                onClick = patchChemotherapy,
            ) {
                ProfileChipList(it)
            }
            MyProfileItem(
                title = stringResource(R.string.radiation_therapy),
                profileProperty = profile.radiationTherapy,
                emptyDescription = stringResource(R.string.empty_radiation_therapy_description),
                onClick = patchRadiationTherapy,
            ) {
                ProfileChipList(it)
            }
            MyProfileItem(
                title = stringResource(R.string.age_group),
                profileProperty = profile.ageGroup,
                emptyDescription = stringResource(R.string.empty_age_group_description),
                onClick = patchAgeGroup,
            ) {
                ProfileChip(text = stringResource(it.description))
            }
            MyProfileItem(
                title = stringResource(R.string.residence_area),
                profileProperty = profile.residenceArea,
                emptyDescription = stringResource(R.string.empty_residence_area_description),
                onClick = patchResidenceArea,
            ) {
                ProfileChip(text = it)
            }
            MyProfileItem(
                title = stringResource(R.string.hospital_area),
                profileProperty = profile.hospitalArea,
                emptyDescription = stringResource(R.string.empty_hospital_area_description),
                needDivider = false,
                onClick = patchHospitalArea,
            ) {
                ProfileChip(text = it)
            }
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
                OthersProfileItem(
                    title = stringResource(R.string.diagnose),
                    data = profile.diagnoses
                ) {
                    ProfileChipList(it)
                }
                OthersProfileItem(
                    title = stringResource(R.string.surgery),
                    data = profile.surgery,
                ) {
                    ProfileChipList(it)
                }
                OthersProfileItem(
                    title = stringResource(R.string.chemotherapy),
                    data = profile.chemotherapy,
                ) {
                    ProfileChipList(it)
                }
                OthersProfileItem(
                    title = stringResource(R.string.radiation_therapy),
                    data = profile.radiationTherapy,
                ) {
                    ProfileChipList(it)
                }
                OthersProfileItem(
                    title = stringResource(R.string.age_group),
                    data = profile.ageGroup,
                ) {
                    ProfileChip(text = stringResource(it.description))
                }
                OthersProfileItem(
                    title = stringResource(R.string.residence_area),
                    data = profile.residenceArea,
                ) {
                    ProfileChip(text = it)
                }
                OthersProfileItem(
                    title = stringResource(R.string.hospital_area),
                    data = profile.hospitalArea,
                    needDivider = false,
                ) {
                    ProfileChip(text = it)
                }
            }
        }
    }
}

@Composable
private fun ProfileLockChip() {
    Row(
        modifier = Modifier
            .background(
                color = Slate100,
                shape = MaterialTheme.shapes.large,
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LockIcon()
        Text(
            text = stringResource(R.string.not_opened),
            style = MaterialTheme.typography.labelSmall,
            color = Slate400,
        )
    }
}

@Composable
private fun <T> MyProfileItem(
    modifier: Modifier = Modifier,
    title: String,
    profileProperty: ProfileProperty<T>,
    emptyDescription: String = "",
    needDivider: Boolean = true,
    onClick: () -> Unit,
    content: @Composable (T) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onClick)
                .padding(
                    horizontal = screenPadding,
                    vertical = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ProfileTitle(text = title)
                    if (profileProperty.open.not()) {
                        ProfileLockChip()
                    }
                }
                RightIcon(color = Slate900)
            }
            val data = profileProperty.data
            if (data != null) {
                content(data)
            } else {
                Text(
                    text = emptyDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500,
                )
            }
        }
        if (needDivider) {
            ProfileDivider()
        }
    }
}

@Composable
private fun <T> OthersProfileItem(
    modifier: Modifier = Modifier,
    title: String,
    data: T?,
    needDivider: Boolean = true,
    content: @Composable (T) -> Unit,
) {
    if (data != null) {
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
                content(data)
            }
            if (needDivider) {
                ProfileDivider()
            }
        }
    }
}