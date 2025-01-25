package com.example.spa.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
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
    val data: Sesi
)

@Serializable
data class Sesi(
    @SerialName("id_sesi")
    val idSesi: Int,
    @SerialName("id_pasien")
    val idPasien: Int,
    @SerialName("id_terapis")
    val idTerapis: Int,
    @SerialName("id_jenisTrapi")
    val idJenisTrapi: Int,
    val tanggalSesi: String,
    val catatanSesi: String
)