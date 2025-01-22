package com.example.spa.dependenciesinjection

import com.example.spa.repository.NetworkKontakSesiRepository
import com.example.spa.repository.SesiRepository
import com.example.spa.service_api.SesiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppSesiContainer{
    val kontakSesiRepository: SesiRepository
}

class SesiContainer: AppSesiContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/sesi/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val sesiService: SesiService by lazy {
        retrofit.create(SesiService::class.java)
    }

    override val kontakSesiRepository: SesiRepository by lazy {
        NetworkKontakSesiRepository(sesiService)
    }
}