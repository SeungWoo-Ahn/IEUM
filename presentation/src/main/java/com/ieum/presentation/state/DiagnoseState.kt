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
    data class Show(val callback: (CancerStage?) -> Unit) : CancerStageSheetUiState

    data object Dismiss : CancerStageSheetUiState
}

class CancerStageSheetState {
    var uiState by mutableStateOf<CancerStageSheetUiState>(CancerStageSheetUiState.Dismiss)
        private set

    val cancerStageState = SingleSelectorState(itemList = CancerStage.entries + listOf(null))

    fun show(cancerStage: CancerStage?, callback: (CancerStage?) -> Unit) {
        cancerStageState.selectItem(cancerStage)
        uiState = CancerStageSheetUiState.Show(callback)
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
                if (it == diagnose) {
                    it.copy(stage = stage)
                } else {
                    it
                }
            }
        }
        cancerStageSheetState.show(diagnose.stage, callback)
    }

    fun validate(): Boolean {
        return diagnoseList.any { it.stage != null }
    }

    fun getDiagnoseList(): List<CancerDiagnose> =
        diagnoseList.mapNotNull { uiModel ->
            uiModel.stage?.let { stage ->
                uiModel.key.toDomain(stage)
            }
        }

}

class DiagnoseState {
    val diagnoseState = MultipleSelectorState(itemList = DiagnoseKey.entries)
    val cancerDiagnoseState = CancerDiagnoseState()

    fun validate(): Boolean {
        return diagnoseState.validate() || cancerDiagnoseState.validate()
    }

    fun getDiagnoseList(): List<Diagnose> =
        cancerDiagnoseState.getDiagnoseList() +
                diagnoseState.selectedItemList.map(DiagnoseKey::toDomain)
}