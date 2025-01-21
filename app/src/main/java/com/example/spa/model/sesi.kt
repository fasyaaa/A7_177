package com.example.spa.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.sql.Date

@Serializable
data class AllSesiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Sesi>
)

@Serializable
data class SesiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: List<Sesi>
)

@Serializable
data class Sesi(
    val id_sesi: String,
    val id_pasien: String,
    val id_terapis: String,
    val id_jenisTrapi: String,
    @Contextual
    val tanggal_sesi: Date,
    val catatan_sesi: String
)