package com.example.appcars.home.data.model

data class CreateHotWheelsRequest(
    val nombre: String,
    val modelo: String,
    val color: String,
    val anioFabricacion: Int,
    val estado: String,
    val imagenes: List<ByteArray>
)