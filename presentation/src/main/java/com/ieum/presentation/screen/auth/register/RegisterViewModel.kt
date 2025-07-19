package com.ieum.presentation.screen.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.design_system.selector.SingleSelectorState
import com.ieum.design_system.textfield.MaxLengthTextField
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.UserType
import com.ieum.domain.usecase.user.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private var uiState by mutableStateOf<RegisterUiState>(RegisterUiState.Idle)

    var currentStage by mutableStateOf(RegisterStage.SelectUserType)
        private set

    val userTypeState = SingleSelectorState(itemList = UserType.entries)
    val nickNameState = MaxLengthTextField(maxLength = 20)
    // diagnose
    val ageGroupState = SingleSelectorState(itemList = AgeGroup.entries)
    // residence
    // hospital
    val interestState = MaxLengthTextField(maxLength = 50)

    fun onPrevStep() {
        if (currentStage == RegisterStage.entries.first()) {
            // 뒤로 돌아가기
        } else {
            val prevIdx = RegisterStage.entries.indexOf(currentStage) - 1
            currentStage = RegisterStage.entries[prevIdx]
        }
    }

    fun onNextStep() {
        if (currentStage == RegisterStage.entries.last()) {
            // 회원 가입 로직 실행
//            register()
        } else {
            val nextIdx = RegisterStage.entries.indexOf(currentStage) + 1
            currentStage = RegisterStage.entries[nextIdx]
        }
    }

    fun nextEnabled(): Boolean =
        when (currentStage) {
            RegisterStage.SelectUserType -> true
            RegisterStage.TypeNickname -> nickNameState.validate()
            RegisterStage.SelectAgeGroup -> ageGroupState.validate()
            RegisterStage.TypeInterest -> interestState.validate() && uiState != RegisterUiState.Loading
        }

    private fun register() {
        viewModelScope.launch {
            uiState = RegisterUiState.Loading
            val registerRequest = RegisterRequest(
                userType = userTypeState.selectedItem!!,
                nickName = nickNameState.getTrimmedText(),
                diagnoses = listOf(),
                ageGroup = ageGroupState.selectedItem,
                residenceArea = null,
                hospitalArea = null,
            )
            registerUseCase(registerRequest)
                .onSuccess {
                    // welcome screen 이동
                }
                .onFailure {
                    // 회원 가입 실패
                    uiState = RegisterUiState.Idle
                }
        }
    }
}