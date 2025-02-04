package com.example.appcars.home.data.datasource

import com.example.appcars.home.data.model.CreateHotWheelsRequest
import com.example.appcars.home.data.model.HotWheelsCar
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface HotWheelsService {
    @GET("cars")
    suspend fun getUserCars(
        @Header("Authorization") token: String
    ): Response<List<HotWheelsCar>>

    @Multipart
    @POST("cars")
    suspend fun createCar(
        @Header("Authorization") token: String,
        @Part nombre: MultipartBody.Part,
        @Part modelo: MultipartBody.Part,
        @Part color: MultipartBody.Part,
        @Part anioFabricacion: MultipartBody.Part,
        @Part estado: MultipartBody.Part,
        @Part imagen: MultipartBody.Part
    ): Response<HotWheelsCar>
}