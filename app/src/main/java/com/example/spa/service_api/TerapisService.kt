package com.example.spa.service_api

import com.example.spa.model.AllPasienResponse
import com.example.spa.model.AllTerapisResponse
import com.example.spa.model.Pasien
import com.example.spa.model.PasienDetailResponse
import com.example.spa.model.Terapis
import com.example.spa.model.TerapisDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TerapisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertTerapis(@Body terapis: Terapis)

    @GET(".")
    suspend fun getAllTerapis(): AllTerapisResponse

    @GET("{id_terapis}")
    suspend fun getTerapisbyIdTerapis(@Path("id_terapis")idterapis: Int): TerapisDetailResponse

    @PUT("{id_terapis}")
    suspend fun updateTerapis(@Path("id_terapis")idterapis: Int, @Body terapis: Terapis)

    @DELETE("{id_terapis}")
    suspend fun deleteTerapis(@Path("id_terapis")idterapis: Int): Response<Void>
}