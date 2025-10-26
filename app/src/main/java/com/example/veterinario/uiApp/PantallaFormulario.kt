package com.example.veterinario.uiApp

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.veterinario.viewmodel.AnimalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormulario(
    viewModel: AnimalViewModel,
    onNavigateBack: () -> Unit
) {
    val lanzadorGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            viewModel.onFotoUriChange(uri)
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Animal") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //Formulario con Validaciones
            CampoTextoValidado(
                valor = viewModel.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = "Nombre",
                esError = viewModel.nombreError,
                errorMsg = "El nombre no puede estar vacío"
            )

            CampoTextoValidado(
                valor = viewModel.tipo,
                onValueChange = { viewModel.onTipoChange(it) },
                label = "Tipo (Ej: Perro, Gato)",
                esError = viewModel.tipoError,
                errorMsg = "El tipo no puede estar vacío"
            )

            OutlinedTextField(
                value = viewModel.edad,
                onValueChange = { viewModel.onEdadChange(it) },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.descripcion,
                onValueChange = { viewModel.onDescripcionChange(it) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // --- Botón para Galería ---
            Button(
                onClick = { lanzadorGaleria.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SELECCIONAR FOTO")
            }

            // --- Previsualización de la Imagen ---
            if (viewModel.fotoUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(viewModel.fotoUri), // Aquí sí se usa la URI temporal
                    contentDescription = "Foto seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 8.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    "Por favor, selecciona una foto (obligatorio).",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.guardarAnimal(onGuardado = onNavigateBack)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.nombreError && !viewModel.tipoError && viewModel.fotoUri != null
            ) {
                Text("GUARDAR ANIMAL")
            }
        }
    }
}

//Aqui se validan los campos
@Composable
fun CampoTextoValidado(
    valor: String,
    onValueChange: (String) -> Unit,
    label: String,
    esError: Boolean,
    errorMsg: String
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = esError,
        trailingIcon = {
            if (esError) Icon(Icons.Filled.Warning, "Error")
        },
        supportingText = {
            if (esError) Text(errorMsg)
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}