package com.example.veterinario.uiApp

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
// ------------------------------------------
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
        composable(
            route = "lista_animales",
            // Define animaciones de entrada y salida
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            }
        ) {
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
        composable(
            route = "formulario",
            // Define animaciones (esta entra desde la izquierda)
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            },
            // Animaciones para "volver" (pop)
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            }
        ) {
            PantallaFormulario(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Ruta 3: Pantalla de Detalle
        composable(
            route = "detalle_animal/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.IntType }),
            // Define animaciones (esta también entra desde la izquierda)
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            }
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