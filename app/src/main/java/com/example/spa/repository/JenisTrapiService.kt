package com.example.spa.repository

import com.example.spa.model.AllJenisTrapiResponse
import com.example.spa.model.JenisTrapi
import com.example.spa.service_api.JenisTrapiService
import okio.IOException

interface JenisTrapiRepository {
    suspend fun insertJenisTrapi(jenisTrapi: JenisTrapi)

    suspend fun updateJenisTrapi(idjenistrapi: Int, jenisTrapi: JenisTrapi)

    suspend fun deleteJenisTrapi(idjenistrapi: Int)

    suspend fun getJenisTrapi(): AllJenisTrapiResponse

    suspend fun getJenisTrapibyIdJenisTrapi(idjenistrapi: Int): JenisTrapi
}

class NetworkKontakJenisTrapiRepository(
    private val kontakJenisTrapiApiService: JenisTrapiService
): JenisTrapiRepository {
    override suspend fun insertJenisTrapi(jenisTrapi: JenisTrapi) {
        kontakJenisTrapiApiService.insertJenisTrapi(jenisTrapi)
    }

    override suspend fun getJenisTrapi(): AllJenisTrapiResponse {
        return kontakJenisTrapiApiService.getAllJenisTrapi()
    }

    override suspend fun updateJenisTrapi(idjenistrapi: Int, jenisTrapi: JenisTrapi) {
        kontakJenisTrapiApiService.updateJenisTrapi(idjenistrapi, jenisTrapi)
    }

    override suspend fun deleteJenisTrapi(idjenistrapi: Int) {
        try {
            val response = kontakJenisTrapiApiService.deleteJenisTrapi(idjenistrapi)
            if(!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP Status code:\" + \"${(response.code())}")
            }
            else{
                response.message()
                println(response.message())
            }
        } catch(e: Exception) {
            throw e
        }
    }

    override suspend fun getJenisTrapibyIdJenisTrapi(idjenistrapi: Int): JenisTrapi {
        return kontakJenisTrapiApiService.getJenisTrapibyIdJenisTrapi(idjenistrapi).data
    }

}