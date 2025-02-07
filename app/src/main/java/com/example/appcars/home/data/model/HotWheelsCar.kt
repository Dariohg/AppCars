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
            "https://back-movil.onrender.com${imagenes.first()}"
        } else {
            ""
        }
    }
}

enum class CarCondition {
    NEW_IN_BOX,
    OPENED_WITH_BOX,
    NO_BOX,
    USED
}