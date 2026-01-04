package com.ieum.presentation.screen.main.othersProfile

sealed class OtherProfileEvent {
    data object TogglePostLike : OtherProfileEvent()
}