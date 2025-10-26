package com.example.veterinario.viewmodel

import android.app.Application // 1. IMPORTA APPLICATION
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.veterinario.data.AnimalRepository
import java.lang.IllegalArgumentException

// 2. AÑADE 'application: Application' AL CONSTRUCTOR
class AnimalViewModelFactory(
    private val repository: AnimalRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // 3. PASA LA 'application' AL VIEWMODEL
            return AnimalViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
