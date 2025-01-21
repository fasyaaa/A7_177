package com.example.spa.model

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
    val data: List<Pasien>
)

@Serializable
data class Pasien(
    val id_pasien: String,
    val nama_pasien: String,
    val alamat: String,
    val no_telp: String,
    val tgl_lahir: String,
    val riwayat_medis: String
)