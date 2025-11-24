package com.ieum.presentation.screen.main.home.myProfile

import com.ieum.domain.model.user.MyProfile

sealed class MyProfileUiState {
    data object Loading : MyProfileUiState()

    data object Error : MyProfileUiState()

    data class Success(val profile: MyProfile) : MyProfileUiState()
}

typealias PatchMyProfile = (profile: MyProfile, onFailure: () -> Unit) -> Unit

sealed class MyProfileDialogState {
    data object Idle : MyProfileDialogState()

    data class ShowPatchDiagnoseDialog(
        val profile: MyProfile,
        val patch: PatchMyProfile,
    ) : MyProfileDialogState()
}
