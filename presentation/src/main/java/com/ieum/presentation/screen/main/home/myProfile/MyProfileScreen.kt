package com.ieum.presentation.screen.main.home.myProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ieum.design_system.progressbar.IEUMLoadingComponent
import com.ieum.design_system.theme.Slate50
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.screen.component.MyProfileSection
import com.ieum.presentation.screen.component.MyProfileTobBar
import com.ieum.presentation.util.GlobalValueModel

@Composable
fun MyProfileRoute(
    modifier: Modifier = Modifier,
    moveSetting: () -> Unit,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    MyProfileScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        currentTab = viewModel.currentTab,
        valueModel = viewModel.valueModel,
        onTabClick = viewModel::selectTab,
        moveSetting = moveSetting,
        patchDiagnose = {},
        patchChemotherapy = {},
        patchRadiationTherapy = {},
        patchAgeGroup = {},
        patchResidenceArea = {},
        patchHospitalArea = {},
    )
}

@Composable
private fun MyProfileScreen(
    modifier: Modifier,
    uiState: MyProfileUiState,
    currentTab: MyProfileTab,
    valueModel: GlobalValueModel,
    onTabClick: (MyProfileTab) -> Unit,
    moveSetting: () -> Unit,
    patchDiagnose: () -> Unit,
    patchChemotherapy: () -> Unit,
    patchRadiationTherapy: () -> Unit,
    patchAgeGroup: () -> Unit,
    patchResidenceArea: () -> Unit,
    patchHospitalArea: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Slate50),
    ) {
        MyProfileTobBar(
            currentTab = currentTab,
            onTabClick = onTabClick,
            onSettingClick = moveSetting,
        )
        when (currentTab) {
            MyProfileTab.PROFILE -> {
                when (uiState) {
                    MyProfileUiState.Loading -> IEUMLoadingComponent()
                    is MyProfileUiState.Success -> MyProfileSection(
                        profile = uiState.profile.toUiModel(valueModel),
                        patchDiagnose = patchDiagnose,
                        patchChemotherapy = patchChemotherapy,
                        patchRadiationTherapy = patchRadiationTherapy,
                        patchAgeGroup = patchAgeGroup,
                        patchResidenceArea = patchResidenceArea,
                        patchHospitalArea = patchHospitalArea,
                    )
                }
            }
            MyProfileTab.POST_LIST -> {}
        }
    }
}