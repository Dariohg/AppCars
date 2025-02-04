// HotWheelsCar.kt
package com.example.appcars.home.data.model

import com.example.appcars.core.network.RetrofitHelper

data class HotWheelsCar(
    val id: String,
    val idUsuario: String,
    val nombre: String,
    val modelo: String,
    val color: String,
    val anioFabricacion: Int,
    val estado: String,
    val imagenes: List<String>
) {
    fun getFullImageUrl(): String {
        return if (imagenes.isNotEmpty()) {
            // Construye la URL completa usando la BASE_URL
            "http://10.0.2.2:3000${imagenes.first()}"
        } else {
            "" // URL por defecto o vacía si no hay imágenes
        }
    }
}

enum class CarCondition {
    NEW_IN_BOX,
    OPENED_WITH_BOX,
    NO_BOX,
    USED
}