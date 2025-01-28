package com.example.spa.service_api

import com.example.spa.model.AllSesiResponse
import com.example.spa.model.JenisTrapiDetailResponse
import com.example.spa.model.PasienDetailResponse
import com.example.spa.model.Sesi
import com.example.spa.model.SesiDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SesiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertSesi(@Body sesi: Sesi)

    @GET(".")
    suspend fun getAllSesi(): AllSesiResponse

    @GET("{id_sesi}")
    suspend fun getSesibyIdSesi(@Path("id_sesi")idsesi: Int): SesiDetailResponse

    @PUT("{id_sesi}")
    suspend fun updateSesi(@Path("id_sesi")idsesi: Int, @Body sesi: Sesi)

    @DELETE("{id_sesi}")
    suspend fun deleteSesi(@Path("id_sesi")idsesi: Int): Response<Void>
}