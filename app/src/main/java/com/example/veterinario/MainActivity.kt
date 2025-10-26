package com.example.veterinario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.veterinario.uiApp.AppNavigation
import com.example.veterinario.ui.theme.VeterinarioTheme
import com.example.veterinario.viewmodel.AnimalViewModel
import com.example.veterinario.viewmodel.AnimalViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: AnimalViewModel by viewModels {
        AnimalViewModelFactory(
            (application as MyApplication).repository,
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VeterinarioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}