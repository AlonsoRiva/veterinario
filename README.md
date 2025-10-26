# Proyecto: Veterinaria App 🐾

Aplicación móvil de gestión de adopción de animales, desarrollada como parte de la Evaluación Parcial 2 para la asignatura **DSY1105 - Desarrollo de Aplicaciones Móviles**.

## 1. Integrantes

* Alonso Rivadeneira
* Constanza Rojas

## 2. Descripción del Proyecto

**Veterinaria App** es una aplicación Android nativa que simula la gestión de un refugio de animales. Permite a los administradores registrar nuevos animales para adopción (incluyendo sus fotos) y a los usuarios ver los animales disponibles, consultar sus detalles y contactar al refugio para adoptarlos.

El proyecto está construido con tecnologías modernas de Android, aplicando una arquitectura limpia **MVVM** (Model-View-ViewModel) y persistencia de datos local con **Room**.

## 3. Funcionalidades Implementadas

La aplicación cumple con todos los requisitos de la pauta de evaluación:

### Arquitectura y Navegación
* **Arquitectura MVVM:** Se separa la lógica de negocio (`ViewModel`), los datos (`Repository` y `Room`) y la interfaz de usuario (`Compose`).
* **Navegación Funcional:** Se utiliza `NavHost` de Jetpack Navigation para controlar el flujo entre las tres pantallas principales.
* **Gestión de Estado:** El `ViewModel` gestiona el estado de la UI (listas, campos de formulario, errores), el cual es observado por las vistas de Compose.

### Persistencia y Datos
* **Almacenamiento Local (Room):** Toda la información de los animales (nombre, tipo, edad, etc.) se guarda en una base de datos SQLite local mediante Room.
* **Persistencia de Imágenes:** Las fotos seleccionadas de la galería se copian al **almacenamiento interno** de la app. Esto asegura que las imágenes no desaparezcan al reiniciar la aplicación (solucionando el problema de permisos temporales de la URI).

### Funcionalidades Específicas (CRUD)
* **(Create) Registrar Animal:** Un formulario permite añadir un nuevo animal a la base de datos.
* **(Read) Ver Animales:** Una pantalla principal muestra la lista de todos los animales disponibles para adopción.
* **(Read) Ver Detalle:** Al tocar un animal, se accede a una pantalla con su foto en grande, descripción y opciones.
* **(Update) Marcar como Adoptado:** En la pantalla de detalle, un botón permite marcar el animal como "adoptado", lo que lo elimina de la lista principal.

### Formularios y Recursos Nativos
* **Formularios Validados:** El formulario de registro valida en tiempo real que los campos "Nombre" y "Tipo" no estén vacíos, y que se haya seleccionado una foto. Muestra íconos y mensajes de error.
* **Recurso Nativo 1 (Galería):** El formulario accede a la galería del dispositivo (`ActivityResultContracts.GetContent`) para seleccionar una foto para el animal.
* **Recurso Nativo 2 (Dialer):** La pantalla de detalle tiene un botón "Llamar al Refugio" que abre el marcador telefónico del dispositivo (`Intent.ACTION_DIAL`) con el número precargado.

### Diseño Visual y Animaciones
* **Diseño Visual:** La interfaz está construida con **Material 3** (`Scaffold`, `TopAppBar`, `OutlinedTextField`, `Card`, etc.).
* **Animaciones Funcionales:** Se implementaron animaciones de **transición (slide + fade)** entre pantallas usando `NavHost` para dar una sensación de fluidez al navegar.

## 4. Pasos para Ejecutar

1.  Clonar el repositorio:
    ```bash
    git clone <URL_DEL_REPOSITORIO_GITHUB>
    ```
2.  Abrir el proyecto con la última versión estable de **Android Studio**.
3.  Esperar a que Gradle descargue y sincronice todas las dependencias (Room, Kapt, Coil, Navigation, etc.).
4.  Ejecutar la aplicación en un **emulador** o un **dispositivo físico** Android (API 26 o superior).
