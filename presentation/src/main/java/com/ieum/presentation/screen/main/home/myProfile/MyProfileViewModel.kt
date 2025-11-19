package com.ieum.presentation.screen.main.home.myProfile

import androidx.lifecycle.ViewModel
import com.ieum.domain.usecase.user.GetMyPostListUseCase
import com.ieum.domain.usecase.user.GetMyProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val getMyPostListUseCase: GetMyPostListUseCase,
) : ViewModel() {
}