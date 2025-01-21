package com.example.spa.repository

import com.example.spa.model.AllJenisTrapiResponse
import com.example.spa.model.JenisTrapi
import com.example.spa.service_api.JenisTrapiService
import okio.IOException

interface JenisTrapiRepository {
    suspend fun insertJenisTrapi(jenisTrapi: JenisTrapi)

    suspend fun updateJenisTrapi(idjenistrapi: String, jenisTrapi: JenisTrapi)

    suspend fun deleteJenisTrapi(idjenistrapi: String)

    suspend fun getJenisTrapi(): AllJenisTrapiResponse

    suspend fun getJenisTrapibyIdJenisTrapi(idjenistrapi: String): JenisTrapi
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

    override suspend fun updateJenisTrapi(idjenistrapi: String, jenisTrapi: JenisTrapi) {
        kontakJenisTrapiApiService.updateJenisTrapi(idjenistrapi, jenisTrapi)
    }

    override suspend fun deleteJenisTrapi(idjenistrapi: String) {
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

    override suspend fun getJenisTrapibyIdJenisTrapi(idjenistrapi: String): JenisTrapi {
        return kontakJenisTrapiApiService.getJenisTrapibyIdJenisTrapi(idjenistrapi).data
    }

}