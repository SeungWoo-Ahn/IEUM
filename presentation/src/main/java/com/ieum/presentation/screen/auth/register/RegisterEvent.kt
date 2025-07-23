package com.ieum.presentation.screen.auth.register

sealed class RegisterEvent {
    data object MoveWelcome : RegisterEvent()

    data object MoveBack : RegisterEvent()
}