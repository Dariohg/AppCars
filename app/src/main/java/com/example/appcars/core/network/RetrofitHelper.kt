package com.example.appcars.core.network

import com.example.appcars.home.data.datasource.HotWheelsService
import com.example.appcars.login.data.datasource.LoginService
import com.example.appcars.register.data.datasource.RegisterService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    val registerService: RegisterService by lazy {
        retrofit.create(RegisterService::class.java)
    }

    val hotWheelsService: HotWheelsService by lazy {
        retrofit.create(HotWheelsService::class.java)
    }
}