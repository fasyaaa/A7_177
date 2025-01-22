package com.example.spa.ui.pasien.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.Pasien
import com.example.spa.repository.PasienRepository
import kotlinx.coroutines.launch

class PasienInsertViewModel(
    private val pas: PasienRepository
) : ViewModel(){
    var pasUiState by mutableStateOf(PasInsertUiState())
        private set

    fun updateInsertPasState(pasInsertUiEvent: PasInsertUiEvent){
        pasUiState = PasInsertUiState(pasInsertUiEvent = pasInsertUiEvent)
    }

    suspend fun insertPas(){
        viewModelScope.launch {
            try {
                pas.insertPasien(pasUiState.pasInsertUiEvent.toPas())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class PasInsertUiState(
    val pasInsertUiEvent: PasInsertUiEvent = PasInsertUiEvent()
)

data class PasInsertUiEvent(
    val id_pasien: String = "",
    val nama_pasien: String = "",
    val alamat: String = "",
    val no_telp: String = "",
    val tgl_lahir: String = "",
    val riwayat_medis: String = ""
)

fun PasInsertUiEvent.toPas(): Pasien = Pasien(
    id_pasien = id_pasien,
    nama_pasien = nama_pasien,
    alamat = alamat,
    no_telp = no_telp,
    tgl_lahir = tgl_lahir,
    riwayat_medis = riwayat_medis
)

fun Pasien.toUiStatePas(): PasInsertUiState = PasInsertUiState(
    pasInsertUiEvent = toPasInsertUiEvent()
)

fun Pasien.toPasInsertUiEvent(): PasInsertUiEvent = PasInsertUiEvent(
    id_pasien = id_pasien,
    nama_pasien = nama_pasien,
    alamat = alamat,
    no_telp = no_telp,
    tgl_lahir = tgl_lahir,
    riwayat_medis = riwayat_medis
)