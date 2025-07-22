package com.ieum.presentation.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ieum.domain.model.address.Address
import com.ieum.domain.usecase.address.GetAddressListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed interface AddressUiState {
    data object Loading : AddressUiState

    data class Success(
        val cityList: List<Address> = emptyList(),
        val provinceList: List<Address> = emptyList(),
        val selectedCity: Address? = null,
        val selectedProvince: Address? = null,
    ) : AddressUiState

    data object Error : AddressUiState
}

class AddressState(
    private val getAddressListUseCase: GetAddressListUseCase,
    private val coroutineScope: CoroutineScope,
) {
    var uiState by mutableStateOf<AddressUiState>(AddressUiState.Loading)
        private set

    val cityListScrollState = LazyListState()
    val provinceListScrollState = LazyListState()

    fun getCityList() {
        coroutineScope.launch {
            getAddressListUseCase()
                .onSuccess { cityList ->
                    uiState = AddressUiState.Success(cityList = cityList)
                }
                .onFailure {
                    uiState = AddressUiState.Error
                }
        }
    }

    fun selectCity(city: Address) {
        val state = uiState as? AddressUiState.Success ?: return
        if (city != state.selectedCity) {
            coroutineScope.launch {
                getAddressListUseCase(city.code)
                    .onSuccess { provinceList ->
                        uiState = state.copy(
                            selectedCity = city,
                            provinceList = provinceList,
                            selectedProvince = null,
                        )
                    }

            }
        }
    }

    fun selectProvince(province: Address) {
        val state = uiState as? AddressUiState.Success ?: return
        if (province != state.selectedProvince) {
            uiState = state.copy(selectedProvince = province)
        }
    }

    fun validate(): Boolean {
        val state = uiState as? AddressUiState.Success ?: return false
        return state.selectedCity != null && state.selectedProvince != null
    }

    fun getSelectedProvince(): Address? {
        val state = uiState as? AddressUiState.Success ?: return null
        return state.selectedProvince
    }
}