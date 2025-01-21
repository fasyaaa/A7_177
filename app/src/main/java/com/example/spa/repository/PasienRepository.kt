package com.example.spa.repository

import com.example.spa.model.AllPasienResponse
import com.example.spa.model.Pasien
import com.example.spa.service_api.PasienService
import okio.IOException

interface PasienRepository {
    suspend fun insertPasien(pasien: Pasien)

    suspend fun updatePasien(idpasien: String, pasien: Pasien)

    suspend fun deletePasien(idpasien: String)

    suspend fun getPasien(): AllPasienResponse

    suspend fun getPasienbyIdPasien(idpasien: String): Pasien
}

class NetworkKontakPasienRepository(
    private val kontakPasienApiService: PasienService
): PasienRepository {

    override suspend fun insertPasien(pasien: Pasien) {
        kontakPasienApiService.insertPasien(pasien)
    }

    override suspend fun getPasien(): AllPasienResponse {
        return kontakPasienApiService.getAllPasien()
    }

    override suspend fun updatePasien(idpasien: String, pasien: Pasien) {
        kontakPasienApiService.updatePasien(idpasien, pasien)
    }

    override suspend fun deletePasien(idpasien: String) {
        try {
            val response = kontakPasienApiService.deletePasien(idpasien)
            if (!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP Status code:\" + \"${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }
    }

    override suspend fun getPasienbyIdPasien(idpasien: String): Pasien {
        return kontakPasienApiService.getPasienbyIdPasien(idpasien).data
    }
}

