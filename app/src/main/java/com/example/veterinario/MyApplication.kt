package com.example.veterinario

import android.app.Application
import com.example.veterinario.data.AppDatabase
import com.example.veterinario.data.AnimalRepository

class MyApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AnimalRepository(database.animalDao()) }
}