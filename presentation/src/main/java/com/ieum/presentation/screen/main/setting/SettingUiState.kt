package com.ieum.presentation.screen.main.setting

sealed class SettingDialogState {
    data object Idle : SettingDialogState()

    data object ShowWithdrawDialog : SettingDialogState()
}