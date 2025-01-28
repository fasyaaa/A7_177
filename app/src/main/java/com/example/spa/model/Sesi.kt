package com.example.spa.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    @SerialName("tanggal_sesi")
    val tanggalSesi: String,
    @SerialName("catatan_sesi")
    val catatanSesi: String,
    @SerialName("nama_pasien")
    val namaPasien: String,
    @SerialName("nama_terapis")
    val namaTerapis: String,
    @SerialName("nama_jenisTrapi")
    val namaJenisTrapi: String
)