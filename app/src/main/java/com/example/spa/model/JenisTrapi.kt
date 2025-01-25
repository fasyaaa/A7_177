package com.example.spa.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllJenisTrapiResponse(
    val status: Boolean,
    val message: String,
    val data: List<JenisTrapi>
)

@Serializable
data class JenisTrapiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: JenisTrapi
)

@Serializable
data class JenisTrapi(
    @SerialName("id_jenisTrapi")
    val idJenisTrapi: Int,
    @SerialName("nama_jenisTrapi")
    val namaJenisTrapi: String,
    @SerialName("deskripsi_jenisTrapi")
    val deskripsiJenisTrapi: String
)