package com.example.spa.service_api

import com.example.spa.model.AllPasienResponse
import com.example.spa.model.Pasien
import com.example.spa.model.PasienDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PasienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertPasien(@Body pasien: Pasien)

    @GET(".")
    suspend fun getAllPasien(): AllPasienResponse

    @GET("{id_pasien}")
    suspend fun getPasienbyIdPasien(@Path("id_pasien")idpasien: String): PasienDetailResponse

    @PUT("{id_pasien}")
    suspend fun updatePasien(@Path("id_pasien")idpasien: String, @Body pasien: Pasien)

    @DELETE("{id_pasien}")
    suspend fun deletePasien(@Path("id_pasien")idpasien: String): Response<Void>
}