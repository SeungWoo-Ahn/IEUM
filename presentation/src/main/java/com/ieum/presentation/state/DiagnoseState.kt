package com.ieum.presentation.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ieum.design_system.selector.MultipleSelectorState
import com.ieum.design_system.selector.SingleSelectorState
import com.ieum.domain.model.user.CancerDiagnose
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.Diagnose
import com.ieum.presentation.mapper.toDomain
import com.ieum.presentation.model.user.CancerDiagnoseKey
import com.ieum.presentation.model.user.CancerDiagnoseUiModel
import com.ieum.presentation.model.user.DiagnoseKey

sealed interface CancerStageSheetUiState {
    data class Show(
        val key: CancerDiagnoseKey,
        val callback: (CancerStage?) -> Unit
    ) : CancerStageSheetUiState

    data object Dismiss : CancerStageSheetUiState
}

class CancerStageSheetState {
    var uiState by mutableStateOf<CancerStageSheetUiState>(CancerStageSheetUiState.Dismiss)
        private set

    val cancerStageState = SingleSelectorState(itemList = CancerStage.entries + listOf(null))

    fun show(diagnose: CancerDiagnoseUiModel, callback: (CancerStage?) -> Unit) {
        cancerStageState.setItem(diagnose.stage)
        uiState = CancerStageSheetUiState.Show(diagnose.key, callback)
    }

    fun dismiss() {
        uiState = CancerStageSheetUiState.Dismiss
    }
}

class CancerDiagnoseState {
    var diagnoseList by mutableStateOf(
        CancerDiagnoseKey.entries.map { CancerDiagnoseUiModel(key = it) }
    )
        private set

    val cancerStageSheetState = CancerStageSheetState()

    fun onDiagnose(diagnose: CancerDiagnoseUiModel) {
        val callback: (CancerStage?) -> Unit = { stage ->
            diagnoseList = diagnoseList.map {
                if (it.key == diagnose.key) {
                    it.copy(stage = stage)
                } else {
                    it
                }
            }
        }
        cancerStageSheetState.show(diagnose, callback)
    }

    fun isSelected(diagnose: CancerDiagnoseUiModel): Boolean {
        return diagnose.stage != null
    }

    fun getSelectedCnt(): Int {
        return diagnoseList.count { it.stage != null }
    }

    fun getSelectedDiagnoseList(): List<CancerDiagnose> =
        diagnoseList.mapNotNull { uiModel ->
            uiModel.stage?.let { stage ->
                uiModel.key.toDomain(stage)
            }
        }

}

class DiagnoseState {
    val commonDiagnoseState = MultipleSelectorState(itemList = DiagnoseKey.entries)
    val cancerDiagnoseState = CancerDiagnoseState()

    fun getSelectedCnt(): Int =
        cancerDiagnoseState.getSelectedCnt() + commonDiagnoseState.selectedItemList.size

    fun validate(): Boolean {
        return getSelectedCnt() > 0
    }

    fun getSelectedDiagnoseList(): List<Diagnose> =
        cancerDiagnoseState.getSelectedDiagnoseList() +
                commonDiagnoseState.selectedItemList.map(DiagnoseKey::toDomain)
}