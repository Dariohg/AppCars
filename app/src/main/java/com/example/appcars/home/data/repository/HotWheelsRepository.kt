package com.example.appcars.home.data.repository

import com.example.appcars.core.network.RetrofitHelper
import com.example.appcars.home.data.model.CreateHotWheelsRequest
import com.example.appcars.home.data.model.HotWheelsCar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class HotWheelsRepository {
    private val hotWheelsService = RetrofitHelper.hotWheelsService

    suspend fun getUserCars(token: String): Result<List<HotWheelsCar>> {
        return try {
            println("DEBUG: Repository - Getting cars with token: $token")
            val response = hotWheelsService.getUserCars("Bearer $token")
            println("DEBUG: Repository - Response code: ${response.code()}")
            println("DEBUG: Repository - Response body: ${response.body()}")
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createCar(token: String, request: CreateHotWheelsRequest): Result<HotWheelsCar> {
        return try {
            println("Creando carro con token: $token")

            // MediaType para los campos de texto
            val mediaType = "text/plain; charset=utf-8".toMediaType()

            // Crear MultipartBody.Part para cada campo
            val nombrePart = MultipartBody.Part.createFormData("nombre", request.nombre)
            val modeloPart = MultipartBody.Part.createFormData("modelo", request.modelo)
            val colorPart = MultipartBody.Part.createFormData("color", request.color)
            val anioFabricacionPart = MultipartBody.Part.createFormData("anioFabricacion", request.anioFabricacion.toString())
            val estadoPart = MultipartBody.Part.createFormData("estado", request.estado)

            // Crear MultipartBody.Part para la imagen con el tipo MIME correcto
            val imagePart = if (request.imagenes.isNotEmpty()) {
                val imageBytes = request.imagenes.first()
                val requestFile = imageBytes.toRequestBody("image/jpeg".toMediaType())  // o "image/png" según corresponda
                MultipartBody.Part.createFormData("imagenes", "image.jpg", requestFile)
            } else {
                throw Exception("Se requiere al menos una imagen")
            }

            println("Enviando request al servidor...")

            val response = hotWheelsService.createCar(
                token = "Bearer $token",
                nombre = nombrePart,
                modelo = modeloPart,
                color = colorPart,
                anioFabricacion = anioFabricacionPart,
                estado = estadoPart,
                imagen = imagePart
            )

            println("Respuesta del servidor: ${response.code()}")
            if (response.isSuccessful) {
                println("Respuesta exitosa: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                println("Error en respuesta: $errorBody")
                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            println("Excepción al crear carro: ${e.message}")
            Result.failure(e)
        }
    }
}