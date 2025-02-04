package com.example.appcars.home.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import com.example.appcars.home.data.model.CarCondition
import com.example.appcars.home.data.model.CreateHotWheelsRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHotWheelsDialog(
    onDismiss: () -> Unit,
    onCarAdded: (CreateHotWheelsRequest) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf(CarCondition.NEW_IN_BOX) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showError by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Agregar Hot Wheels",
                    style = MaterialTheme.typography.titleLarge
                )

                // Imagen
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                            Text("No hay imagen seleccionada")
                        }
                    }
                }

                Button(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AddPhotoAlternate, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Seleccionar Imagen")
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = model,
                    onValueChange = { model = it },
                    label = { Text("Modelo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = color,
                    onValueChange = { color = it },
                    label = { Text("Color") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = year,
                    onValueChange = { if (it.all { char -> char.isDigit() }) year = it },
                    label = { Text("Año de fabricación") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it }
                ) {
                    OutlinedTextField(
                        value = when (condition) {
                            CarCondition.NEW_IN_BOX -> "Nuevo en caja"
                            CarCondition.OPENED_WITH_BOX -> "Abierto con caja"
                            CarCondition.NO_BOX -> "Sin caja"
                            CarCondition.USED -> "Usado"
                        },
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Estado") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        CarCondition.entries.forEach { conditionOption ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        when (conditionOption) {
                                            CarCondition.NEW_IN_BOX -> "Nuevo en caja"
                                            CarCondition.OPENED_WITH_BOX -> "Abierto con caja"
                                            CarCondition.NO_BOX -> "Sin caja"
                                            CarCondition.USED -> "Usado"
                                        }
                                    )
                                },
                                onClick = {
                                    condition = conditionOption
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }

                if (showError) {
                    Text(
                        text = if (imageUri == null)
                            "Por favor selecciona una imagen"
                        else
                            "Por favor completa todos los campos",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            if (name.isBlank() || model.isBlank() ||
                                color.isBlank() || year.isBlank() ||
                                imageUri == null) {
                                showError = true
                                return@Button
                            }

                            try {
                                val inputStream = context.contentResolver.openInputStream(imageUri!!)
                                val imageBytes = inputStream?.use { it.readBytes() }

                                if (imageBytes == null) {
                                    showError = true
                                    return@Button
                                }

                                val mimeType = context.contentResolver.getType(imageUri!!)
                                if (mimeType?.startsWith("image/") != true) {
                                    showError = true
                                    return@Button
                                }

                                println("Intentando agregar carro:")
                                println("Nombre: $name")
                                println("Modelo: $model")
                                println("Color: $color")
                                println("Año: $year")
                                println("Estado: ${when(condition) {
                                    CarCondition.NEW_IN_BOX -> "nuevo"
                                    CarCondition.OPENED_WITH_BOX -> "abierto"
                                    CarCondition.NO_BOX -> "sin_caja"
                                    CarCondition.USED -> "usado"
                                }}")
                                println("Tamaño de imagen: ${imageBytes.size} bytes")

                                onCarAdded(
                                    CreateHotWheelsRequest(
                                        nombre = name,
                                        modelo = model,
                                        color = color,
                                        anioFabricacion = year.toInt(),
                                        estado = when(condition) {
                                            CarCondition.NEW_IN_BOX -> "nuevo"
                                            CarCondition.OPENED_WITH_BOX -> "abierto"
                                            CarCondition.NO_BOX -> "sin_caja"
                                            CarCondition.USED -> "usado"
                                        },
                                        imagenes = listOf(imageBytes)
                                    )
                                )
                            } catch (e: Exception) {
                                println("Error al crear el request: ${e.message}")
                                showError = true
                            }
                        }
                    ) {
                        Text("Agregar")
                    }
                }
            }
        }
    }
}