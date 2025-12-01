package com.ieum.presentation.state

import androidx.compose.runtime.toMutableStateList
import com.ieum.design_system.selector.MultipleSelectorState
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.Diagnose
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.mapper.toUiKey
import com.ieum.presentation.mapper.toUiModel
import com.ieum.presentation.model.user.CancerDiagnoseUiKey
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.CancerStageUiModel
import com.ieum.presentation.model.user.DiagnoseUiKey

class CancerDiagnoseState {
    private val _itemList = CancerDiagnoseUiKey.entries.map {
        CancerDiagnoseUiModel(key = it, stage = CancerStageUiModel.STAGE_UNKNOWN)
    }.toMutableStateList()
    val itemList: List<CancerDiagnoseUiModel> get() = _itemList

    val selectedCount get() = itemList.count(CancerDiagnoseUiModel::isSelected)

    fun onDiagnose(cancerDiagnose: CancerDiagnoseUiModel) {
        val targetIndex = itemList.indexOfFirst { it.key == cancerDiagnose.key }
        if (targetIndex != -1) {
            _itemList[targetIndex] = cancerDiagnose
        }
    }
}

class DiagnoseState {
    val commonDiagnoseState = MultipleSelectorState(itemList = DiagnoseUiKey.entries)
    val cancerDiagnoseState = CancerDiagnoseState()

    val totalSelectedCount: Int
        get() = cancerDiagnoseState.selectedCount + commonDiagnoseState.selectedItemList.size

    fun setDiagnoseList(diagnoseList: List<Diagnose>) {
        diagnoseList.forEach { diagnose ->
            when (diagnose) {
               is CancerDiagnose -> cancerDiagnoseState.onDiagnose(diagnose.toUiModel())
                else -> {
                    val uiKey = diagnose.name.toUiKey() as DiagnoseUiKey
                    commonDiagnoseState.selectItem(uiKey)
                }
            }
        }
    }

    fun validate(): Boolean {
        return totalSelectedCount > 0
    }

    fun getSelectedDiagnoseList(): List<Diagnose> =
        cancerDiagnoseState.itemList.mapNotNull(CancerDiagnoseUiModel::toDomain) +
                commonDiagnoseState.selectedItemList.map(DiagnoseUiKey::toDomain)
}