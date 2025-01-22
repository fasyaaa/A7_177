package com.example.spa.dependenciesinjection

import com.example.spa.repository.JenisTrapiRepository
import com.example.spa.repository.NetworkKontakJenisTrapiRepository
import com.example.spa.service_api.JenisTrapiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppJenisTrapiContainer {
    val kontakJenisTrapiRepository: JenisTrapiRepository
}

class JenisTrapiContainer: AppJenisTrapiContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/jenisTrapi/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val jenisTrapiService: JenisTrapiService by lazy {
        retrofit.create(JenisTrapiService::class.java)
    }

    override val kontakJenisTrapiRepository: JenisTrapiRepository by lazy {
        NetworkKontakJenisTrapiRepository(jenisTrapiService)
    }
}