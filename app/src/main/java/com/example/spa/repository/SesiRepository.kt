package com.example.spa.repository

import com.example.spa.model.AllSesiResponse
import com.example.spa.model.Sesi
import com.example.spa.service_api.SesiService
import okio.IOException

interface SesiRepository {
    suspend fun insertSesi(sesi: Sesi)

    suspend fun updateSesi(idsesi: Int, sesi: Sesi)

    suspend fun deleteSesi(idsesi: Int)

    suspend fun getSesi(): AllSesiResponse

    suspend fun getSesibyIdSesi(idsesi: Int): Sesi
}

class NetworkKontakSesiRepository(
    private val kontakSesiApiService: SesiService
): SesiRepository {

    override suspend fun insertSesi(sesi: Sesi) {
        kontakSesiApiService.insertSesi(sesi)
    }

    override suspend fun getSesi(): AllSesiResponse {
        return kontakSesiApiService.getAllSesi()
    }

    override suspend fun updateSesi(idsesi: Int, sesi: Sesi) {
        kontakSesiApiService.updateSesi(idsesi, sesi)
    }

    override suspend fun deleteSesi(idsesi: Int) {
        try {
            val response = kontakSesiApiService.deleteSesi(idsesi)
            if(!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP Status code:" + "${(response.code())}")
            } else {
                response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }
    }

    override suspend fun getSesibyIdSesi(idsesi: Int): Sesi {
        return kontakSesiApiService.getSesibyIdSesi(idsesi).data
    }

}