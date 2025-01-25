package com.example.spa.repository

import com.example.spa.model.AllPasienResponse
import com.example.spa.model.AllTerapisResponse
import com.example.spa.model.Pasien
import com.example.spa.model.Terapis
import com.example.spa.service_api.PasienService
import com.example.spa.service_api.TerapisService
import okio.IOException


interface TerapisRepository {
    suspend fun insertTerapis(terapis: Terapis)

    suspend fun updateTerapis(idterapis: Int, terapis: Terapis)

    suspend fun deleteTerapis(idterapis: Int)

    suspend fun getTerapis(): AllTerapisResponse

    suspend fun getTerapisbyIdTerapis(idterapis: Int): Terapis
}

class NetworkKontakTerapisRepository(
    private val kontakTerapisApiService: TerapisService
): TerapisRepository {

    override suspend fun insertTerapis(terapis: Terapis) {
        kontakTerapisApiService.insertTerapis(terapis)
    }

    override suspend fun getTerapis(): AllTerapisResponse {
        return kontakTerapisApiService.getAllTerapis()
    }

    override suspend fun updateTerapis(idterapis: Int, terapis: Terapis) {
        kontakTerapisApiService.updateTerapis(idterapis, terapis)
    }

    override suspend fun deleteTerapis(idterapis: Int) {
        try {
            val response = kontakTerapisApiService.deleteTerapis(idterapis)
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

    override suspend fun getTerapisbyIdTerapis(idterapis: Int): Terapis {
        return kontakTerapisApiService.getTerapisbyIdTerapis(idterapis).data
    }
}