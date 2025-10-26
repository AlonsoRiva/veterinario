
package com.example.veterinario.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animales")
data class Animal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val tipo: String, // "Perro", "Gato", etc.
    val edad: Int,
    val descripcion: String,
    val fotoUri: String, // Path de la imagen
    val adoptado: Boolean = false
)