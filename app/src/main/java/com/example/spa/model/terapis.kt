package com.example.spa.model

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
    val data: List<Terapis>
)

@Serializable
data class Terapis(
    val id_terapis: String,
    val nama_terapis: String,
    val spesialis: String,
    val no_izin_prak: String
)