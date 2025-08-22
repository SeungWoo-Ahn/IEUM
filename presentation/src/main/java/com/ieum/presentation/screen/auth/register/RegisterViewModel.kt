package com.ieum.presentation.screen.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieum.design_system.selector.SingleSelectorState
import com.ieum.design_system.textfield.MaxLengthTextFieldState
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.usecase.address.GetAddressListUseCase
import com.ieum.domain.usecase.user.RegisterUseCase
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.model.user.AgeGroupUiModel
import com.ieum.presentation.model.user.SexUiModel
import com.ieum.presentation.model.user.UserTypeUiModel
import com.ieum.presentation.state.AddressState
import com.ieum.presentation.state.DiagnoseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    getAddressListUseCase: GetAddressListUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private var uiState by mutableStateOf<RegisterUiState>(RegisterUiState.Idle)

    private val _event = MutableSharedFlow<RegisterEvent>()
    val event: SharedFlow<RegisterEvent> = _event.asSharedFlow()

    var currentStage by mutableStateOf(RegisterStage.SelectUserType)
        private set

    val userTypeState = SingleSelectorState(itemList = UserTypeUiModel.entries)
    val sexState = SingleSelectorState(itemList = SexUiModel.entries)
    val nickNameState = MaxLengthTextFieldState(maxLength = 20)
    val diagnoseState = DiagnoseState()
    val ageGroupState = SingleSelectorState(itemList = AgeGroupUiModel.entries)
    val residenceState = AddressState(
        getAddressListUseCase = getAddressListUseCase,
        coroutineScope = viewModelScope,
    )
    val hospitalState = AddressState(
        getAddressListUseCase = getAddressListUseCase,
        coroutineScope = viewModelScope,
    )
    val interestState = MaxLengthTextFieldState(maxLength = 50)

    fun onPrevStep() {
        if (currentStage == RegisterStage.entries.first()) {
            viewModelScope.launch {
                _event.emit(RegisterEvent.MoveBack)
            }
        } else {
            val prevIdx = RegisterStage.entries.indexOf(currentStage) - 1
            currentStage = RegisterStage.entries[prevIdx]
        }
    }

    fun onNextStep() {
        if (currentStage == RegisterStage.entries.last()) {
            register()
        } else {
            val nextIdx = RegisterStage.entries.indexOf(currentStage) + 1
            currentStage = RegisterStage.entries[nextIdx]
        }
    }

    fun nextEnabled(): Boolean =
        when (currentStage) {
            RegisterStage.SelectUserType, RegisterStage.SelectSex -> true
            RegisterStage.TypeNickname -> nickNameState.validate()
            RegisterStage.SelectDiagnose -> diagnoseState.validate()
            RegisterStage.SelectAgeGroup -> ageGroupState.validate()
            RegisterStage.SelectResidence -> residenceState.validate()
            RegisterStage.SelectHospital -> hospitalState.validate()
            RegisterStage.TypeInterest -> interestState.validate() && uiState != RegisterUiState.Loading
        }

    private fun register() {
        viewModelScope.launch {
            uiState = RegisterUiState.Loading
            val registerRequest = RegisterRequest(
                userType = userTypeState.selectedItem!!.toDomain(),
                sex = sexState.selectedItem!!.toDomain(),
                nickName = nickNameState.getTrimmedText(),
                diagnoses = diagnoseState.getSelectedDiagnoseList(),
                ageGroup = ageGroupState.selectedItem?.toDomain(),
                residenceArea = residenceState.getSelectedProvince()?.fullName,
                hospitalArea = hospitalState.getSelectedProvince()?.fullName,
            )
            _event.emit(RegisterEvent.MoveWelcome)
/*            registerUseCase(registerRequest)
                .onSuccess {
                    _event.emit(RegisterEvent.MoveWelcome)
                }
                .onFailure {
                    // 회원 가입 실패
                    uiState = RegisterUiState.Idle
                }*/
        }
    }
}