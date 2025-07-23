package com.ieum.presentation.screen.auth.register

import androidx.annotation.StringRes
import com.ieum.presentation.R

enum class RegisterStage(@StringRes val guide: Int) {
    SelectUserType(guide = R.string.guide_select_user_type),
    TypeNickname(guide = R.string.guide_type_nickname),
    SelectDiagnose(guide = R.string.guide_select_diagnose),
    SelectAgeGroup(guide = R.string.guide_select_age_group),
    SelectResidence(guide = R.string.guide_select_residence),
    SelectHospital(guide = R.string.guide_select_hospital),
    TypeInterest(guide = R.string.guide_type_interest),
}