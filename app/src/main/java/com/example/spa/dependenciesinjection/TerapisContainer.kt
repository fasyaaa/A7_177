package com.example.spa.dependenciesinjection

import com.example.spa.repository.NetworkKontakTerapisRepository
import com.example.spa.repository.TerapisRepository
import com.example.spa.service_api.TerapisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppTerapisContainer {
    val kontakTerapisRepository: TerapisRepository
}

class TerapisContainer: AppTerapisContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/terapis/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val terapisService: TerapisService by lazy {
        retrofit.create(TerapisService::class.java)
    }

    override val kontakTerapisRepository: TerapisRepository by lazy {
        NetworkKontakTerapisRepository(terapisService)
    }
}