// components/HotWheelsCard.kt
package com.example.appcars.home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.appcars.home.data.model.HotWheelsCar

@Composable
fun HotWheelsCard(car: HotWheelsCar) {
    val imageUrl = car.getFullImageUrl()
    println("DEBUG: Loading image from URL: $imageUrl")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen
            if (car.imagenes.isNotEmpty()) {
                AsyncImage(
                    model = car.getFullImageUrl(),
                    contentDescription = "Imagen de ${car.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Información
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = car.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = car.modelo,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Color: ${car.color}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Año: ${car.anioFabricacion}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Estado: ${car.estado}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}