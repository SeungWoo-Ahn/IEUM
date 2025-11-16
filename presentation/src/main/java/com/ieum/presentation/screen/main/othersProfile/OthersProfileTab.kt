package com.ieum.presentation.screen.main.othersProfile

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class OthersProfileTab(@StringRes val displayName: Int) {
    PROFILE(displayName = R.string.profile),
    POST_LIST(displayName = R.string.postList),
}