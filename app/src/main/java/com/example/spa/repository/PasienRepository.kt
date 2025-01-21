package com.example.spa.repository

import com.example.spa.model.AllPasienResponse
import com.example.spa.model.Pasien

interface PasienRepository {
    suspend fun insertPasien(pasien: Pasien)

    suspend fun updatePasien(idpasien: String, pasien: Pasien)

    suspend fun deletePasien(idpasien: String)

    suspend fun getPasien(): AllPasienResponse

    suspend fun getPasienbyIdPasien(idpasien: String): Pasien
}

