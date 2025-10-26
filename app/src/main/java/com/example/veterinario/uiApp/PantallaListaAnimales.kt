package com.example.veterinario.uiApp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.veterinario.data.Animal
import com.example.veterinario.viewmodel.AnimalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListaAnimales(
    viewModel: AnimalViewModel,
    onAnimalClick: (Int) -> Unit,
    onNavigateToForm: () -> Unit
) {
    val listaAnimales by viewModel.listaAnimales.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Adopción Veterinaria") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToForm) {
                Icon(Icons.Filled.Add, contentDescription = "Registrar Animal")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listaAnimales) { animal ->
                AnimalCard(animal = animal, onClick = { onAnimalClick(animal.id) })
            }
        }
    }
}

@Composable
fun AnimalCard(animal: Animal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = animal.nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tipo: ${animal.tipo}")
            Text(text = "Edad: ${animal.edad} años")
        }
    }
}

