package com.example.spa.repository

import com.example.spa.model.AllJenisTrapiResponse
import com.example.spa.model.JenisTrapi

interface JenisTrapiService {
    suspend fun insertJenisTrapi(jenisTrapi: JenisTrapi)

    suspend fun updateJenisTrapi(idjenistrapi: String, jenisTrapi: JenisTrapi)

    suspend fun deleteJenisTrapi(idjenistrapi: String)

    suspend fun getJenisTrapi(): AllJenisTrapiResponse

    suspend fun getJenisTrapibyIdJenisTrapi(idjenistrapi: String): JenisTrapi
}

