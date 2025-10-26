package com.example.veterinario.uiApp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.veterinario.viewmodel.AnimalViewModel

@Composable
fun AppNavigation(viewModel: AnimalViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "lista_animales") {

        // Ruta 1: Pantalla de Lista
        composable("lista_animales") {
            PantallaListaAnimales(
                viewModel = viewModel,
                onAnimalClick = { animalId ->
                    navController.navigate("detalle_animal/$animalId")
                },
                onNavigateToForm = {
                    navController.navigate("formulario")
                }
            )
        }

        // Ruta 2: Pantalla de Formulario
        composable("formulario") {
            PantallaFormulario(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Ruta 3: Pantalla de Detalle
        composable(
            route = "detalle_animal/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getInt("animalId") ?: 0
            PantallaDetalleAnimal(
                viewModel = viewModel,
                animalId = animalId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

