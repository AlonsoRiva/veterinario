package com.example.veterinario.uiApp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.veterinario.viewmodel.AnimalViewModel
import java.io.File // 1. IMPORTA FILE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleAnimal(
    viewModel: AnimalViewModel,
    animalId: Int,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(animalId) {
        viewModel.cargarAnimal(animalId)
    }

    val animal by viewModel.animalSeleccionado.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(animal?.nombre ?: "Cargando...") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        animal?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(

                    painter = rememberAsyncImagePainter(File(it.fotoUri)),
                    contentDescription = it.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                Text(it.nombre, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Text("Tipo: ${it.tipo}", style = MaterialTheme.typography.titleLarge)
                Text("Edad: ${it.edad} años", style = MaterialTheme.typography.titleLarge)
                Text("Descripción:", style = MaterialTheme.typography.titleMedium)
                Text(it.descripcion, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        val numeroRefugio = "+56912345678"
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$numeroRefugio")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Call, contentDescription = "Llamar")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("LLAMAR AL REFUGIO")
                }

                Button(
                    onClick = {
                        viewModel.marcarComoAdoptado(it)
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("MARCAR COMO ADOPTADO")
                }
            }
        }
    }
}

