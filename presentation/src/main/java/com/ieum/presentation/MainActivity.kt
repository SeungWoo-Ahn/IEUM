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
import com.ieum.design_system.theme.IEUMTheme
import com.ieum.presentation.screen.IEUMApp
import com.ieum.presentation.screen.rememberIEUMAppState
import dagger.hilt.android.AndroidEntryPoint

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

                if (uiState is MainActivityUiState.Success) {
                    val appState = rememberIEUMAppState()

                    LaunchedEffect(Unit) {
                        viewModel.exceptionCollector.exceptionMessageFlow.collect {
                            showToast(it)
                        }
                    }

                    IEUMApp(
                        appState = appState,
                        isAuthenticated = (uiState as MainActivityUiState.Success).isAuthenticated
                    )
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}