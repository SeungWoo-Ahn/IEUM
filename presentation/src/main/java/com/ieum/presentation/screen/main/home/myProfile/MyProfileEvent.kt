package com.ieum.presentation.screen.main.home.myProfile

sealed class MyProfileEvent {
    data object TogglePostLike : MyProfileEvent()
}