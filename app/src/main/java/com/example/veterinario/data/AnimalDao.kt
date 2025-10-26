package com.example.veterinario.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(animal: Animal)

    @Query("SELECT * FROM animales WHERE adoptado = 0 ORDER BY nombre ASC")
    fun getTodos(): Flow<List<Animal>>

    @Query("SELECT * FROM animales WHERE id = :id")
    fun getPorId(id: Int): Flow<Animal>

    @Update
    suspend fun actualizar(animal: Animal)

    @Delete
    suspend fun eliminar(animal: Animal)
}

