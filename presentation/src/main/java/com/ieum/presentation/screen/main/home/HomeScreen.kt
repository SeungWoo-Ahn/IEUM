package com.ieum.presentation.screen.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ieum.design_system.theme.screenPadding
import com.ieum.presentation.screen.component.AddPostDialog

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    movePostTreatmentRecords: () -> Unit,
    movePostDailyRecords: () -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        movePostTreatmentRecords = movePostTreatmentRecords,
        movePostDailyRecords = movePostDailyRecords,
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier,
    movePostTreatmentRecords: () -> Unit,
    movePostDailyRecords: () -> Unit,
) {
    var showAddPostDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = screenPadding),
            onClick = { showAddPostDialog = true }
        ) {
            Text(
                text = "글쓰기"
            )
        }
        if (showAddPostDialog) {
            AddPostDialog(
                movePostTreatmentRecords = movePostTreatmentRecords,
                movePostDailyRecords = movePostDailyRecords,
                onDismissRequest = { showAddPostDialog = false }
            )
        }
    }
}