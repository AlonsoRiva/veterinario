package com.example.veterinario.data

import kotlinx.coroutines.flow.Flow

class AnimalRepository(private val animalDao: AnimalDao) {

    val todosLosAnimales: Flow<List<Animal>> = animalDao.getTodos()

    fun getAnimal(id: Int): Flow<Animal> {
        return animalDao.getPorId(id)
    }

    suspend fun insertar(animal: Animal) {
        animalDao.insertar(animal)
    }

    suspend fun actualizar(animal: Animal) {
        animalDao.actualizar(animal)
    }
}