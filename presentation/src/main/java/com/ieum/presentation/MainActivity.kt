package com.ieum.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.ieum.design_system.theme.IEUMTheme
import com.ieum.presentation.screen.IEUMApp
import com.ieum.presentation.screen.rememberIEUMAppState
import com.ieum.presentation.util.ExceptionCollector
import com.ieum.presentation.util.MessageCollector
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.merge

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.shouldKeepSplashScreen }

        setContent {
            IEUMTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val isAuthenticated = when (val state = uiState) {
                    MainActivityUiState.Loading -> return@IEUMTheme
                    is MainActivityUiState.Success -> state.isAuthenticated
                }
                val appState = rememberIEUMAppState()

                LaunchedEffect(Unit) {
                    merge(
                        ExceptionCollector.exceptionMessageFlow,
                        MessageCollector.messageFlow,
                    )
                        .flowWithLifecycle(lifecycle)
                        .collect {
                            showToast(it)
                        }
                }

                IEUMApp(
                    appState = appState,
                    isAuthenticated = isAuthenticated
                )
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}