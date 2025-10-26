
package com.example.veterinario.viewmodel

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinario.data.Animal
import com.example.veterinario.data.AnimalRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

// 1. AÑADE 'application: Application' AL CONSTRUCTOR
class AnimalViewModel(
    private val repository: AnimalRepository,
    private val application: Application // Necesario para guardar archivos
) : ViewModel() {

    // --- Estado de la Lista ---
    val listaAnimales: StateFlow<List<Animal>> = repository.todosLosAnimales
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    // --- Estado para el Detalle ---
    private val _animalSeleccionado = MutableStateFlow<Animal?>(null)
    val animalSeleccionado: StateFlow<Animal?> = _animalSeleccionado.asStateFlow()

    fun cargarAnimal(id: Int) {
        viewModelScope.launch {
            repository.getAnimal(id).collect { animal ->
                _animalSeleccionado.value = animal
            }
        }
    }

    // --- Estado del Formulario ---
    var nombre by mutableStateOf("")
    var tipo by mutableStateOf("")
    var edad by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var fotoUri by mutableStateOf<Uri?>(null) // Sigue siendo la URI temporal de la galería

    // --- Estado de Validación ---
    var nombreError by mutableStateOf(false)
    var tipoError by mutableStateOf(false)

    // --- Lógica de UI ---
    fun onNombreChange(valor: String) {
        nombre = valor
        nombreError = valor.isBlank()
    }

    fun onTipoChange(valor: String) {
        tipo = valor
        tipoError = valor.isBlank()
    }

    fun onEdadChange(valor: String) { edad = valor }
    fun onDescripcionChange(valor: String) { descripcion = valor }
    fun onFotoUriChange(uri: Uri?) { fotoUri = uri }

    private fun validarCampos(): Boolean {
        onNombreChange(nombre)
        onTipoChange(tipo)
        return !nombreError && !tipoError && fotoUri != null
    }

    // 2. FUNCIÓN PARA COPIAR LA FOTO AL ALMACENAMIENTO INTERNO
    private fun guardarCopiaLocal(uri: Uri): String {
        val contentResolver = application.contentResolver
        // Crea un nombre de archivo único
        val nombreArchivo = "animal_${System.currentTimeMillis()}.jpg"
        // Crea un archivo nuevo en el directorio interno y privado de la app
        val archivoDestino = File(application.filesDir, nombreArchivo)

        try {
            // Abre el stream de la URI temporal de la galería
            val inputStream = contentResolver.openInputStream(uri)
            // Abre el stream a nuestro nuevo archivo
            val outputStream = FileOutputStream(archivoDestino)

            // Copia los bytes de un archivo a otro
            inputStream?.copyTo(outputStream)

            // Cierra los streams
            inputStream?.close()
            outputStream.close()

        } catch (e: Exception) {
            e.printStackTrace()
            // Si algo falla, devuelve la URI original (aunque podría fallar después)
            return uri.toString()
        }
        // Devuelve la RUTA ABSOLUTA de nuestro archivo copiado
        // Ej: /data/data/com.example.veterinario/files/animal_12345.jpg
        return archivoDestino.absolutePath
    }


    // --- Acciones CRUD ---
    fun guardarAnimal(onGuardado: () -> Unit) {
        // 3. ASEGÚRATE QUE FOTOURI NO SEA NULO
        if (validarCampos() && fotoUri != null) {

            // 4. COPIA LA IMAGEN ANTES DE GUARDAR
            val rutaPermanente = guardarCopiaLocal(fotoUri!!)

            val animal = Animal(
                nombre = nombre,
                tipo = tipo,
                edad = edad.toIntOrNull() ?: 0,
                descripcion = descripcion,
                fotoUri = rutaPermanente, // 5. GUARDA LA RUTA PERMANENTE
                adoptado = false
            )
            viewModelScope.launch {
                repository.insertar(animal)
                limpiarFormulario()
                onGuardado()
            }
        }
    }

    fun marcarComoAdoptado(animal: Animal) {
        viewModelScope.launch {
            repository.actualizar(animal.copy(adoptado = true))
        }
    }

    private fun limpiarFormulario() {
        nombre = ""
        tipo = ""
        edad = ""
        descripcion = ""
        fotoUri = null
    }
}
