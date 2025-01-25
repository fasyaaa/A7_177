package com.example.spa.service_api

import com.example.spa.model.AllJenisTrapiResponse
import com.example.spa.model.JenisTrapi
import com.example.spa.model.JenisTrapiDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisTrapiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("store")
    suspend fun insertJenisTrapi(@Body jenisTrapi: JenisTrapi)

    @GET(".")
    suspend fun getAllJenisTrapi(): AllJenisTrapiResponse

    @GET("{id_jenisTrapi}")
    suspend fun getJenisTrapibyIdJenisTrapi(@Path("id_jenisTrapi")idJenisTrapi: Int): JenisTrapiDetailResponse

    @PUT("{id_jenisTrapi}")
    suspend fun updateJenisTrapi(@Path("id_jenisTrapi")idJenisTrapi: Int, @Body jenisTrapi: JenisTrapi)

    @DELETE("{id_jenisTrapi}")
    suspend fun deleteJenisTrapi(@Path("id_jenisTrapi")idJenisTrapi: Int): Response<Void>
}