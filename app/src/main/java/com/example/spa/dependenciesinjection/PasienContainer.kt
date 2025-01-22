package com.example.spa.dependenciesinjection

import com.example.spa.repository.NetworkKontakPasienRepository
import com.example.spa.repository.PasienRepository
import com.example.spa.service_api.PasienService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppPasienContainer {
    val kontakPasienRepository: PasienRepository
}

class PasienContainer: AppPasienContainer{
    private val baseUrl = "http://10.0.2.2:3000/api/pasien/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pasienService: PasienService by lazy {
        retrofit.create(PasienService::class.java)
    }

    override val kontakPasienRepository: PasienRepository by lazy {
        NetworkKontakPasienRepository(pasienService)
    }
}