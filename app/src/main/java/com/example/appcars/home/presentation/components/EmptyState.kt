// components/EmptyState.kt
package com.example.appcars.home.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No hay Hot Wheels en tu colección",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "¡Agrega tu primer Hot Wheels con el botón +!",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}