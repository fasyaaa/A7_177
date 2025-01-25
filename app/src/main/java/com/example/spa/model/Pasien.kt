package com.example.spa.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllPasienResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pasien>
)

@Serializable
data class PasienDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pasien
)

@Serializable
data class Pasien(
    @SerialName("id_pasien")
    val idPasien: Int,
    @SerialName("nama_pasien")
    val namaPasien: String,
    val alamat: String,
    @SerialName("no_telp")
    val noTelp: String,
    @SerialName("tgl_lahir")
    val tglLahir: String,
    @SerialName("riwayat_medis")
    val riwayatMedis: String
)