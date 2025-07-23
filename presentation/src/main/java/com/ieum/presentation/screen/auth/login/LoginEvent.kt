package com.ieum.presentation.screen.auth.login

sealed class LoginEvent {
    data object MoveMain : LoginEvent()

    data object MoveRegister : LoginEvent()
}