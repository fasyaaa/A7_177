package com.example.spa.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllTerapisResponse(
    val status: Boolean,
    val message: String,
    val data: List<Terapis>
)

@Serializable
data class TerapisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Terapis
)

@Serializable
data class Terapis(
    @SerialName("id_terapis")
    val idTerapis: Int,
    @SerialName("nama_terapis")
    val namaTerapis: String,
    val spesialis: String,
    @SerialName("no_izin_prak")
    val noIzinPrak: String
)