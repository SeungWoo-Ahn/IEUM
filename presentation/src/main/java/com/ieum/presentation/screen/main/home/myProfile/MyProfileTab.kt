package com.ieum.presentation.screen.main.home.myProfile

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class MyProfileTab(@StringRes val displayName: Int) {
    PROFILE(displayName = R.string.my_profile),
    POST_LIST(displayName = R.string.my_post_list),
}