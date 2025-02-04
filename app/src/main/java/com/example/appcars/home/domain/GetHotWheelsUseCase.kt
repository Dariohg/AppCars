package com.example.appcars.home.domain

import com.example.appcars.core.session.SessionManager
import com.example.appcars.home.data.model.HotWheelsCar
import com.example.appcars.home.data.repository.HotWheelsRepository

class GetHotWheelsUseCase(
    private val repository: HotWheelsRepository = HotWheelsRepository()
) {
    suspend operator fun invoke(): Result<List<HotWheelsCar>> {
        val token = SessionManager.getToken()
            ?: return Result.failure(Exception("No hay sesión activa"))

        return repository.getUserCars(token)
    }
}