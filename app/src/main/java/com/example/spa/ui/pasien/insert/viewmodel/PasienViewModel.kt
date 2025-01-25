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
    val pasInsertUiEvent: PasInsertUiEvent = PasInsertUiEvent(0,"","","","","")
)

data class PasInsertUiEvent(
    val idPasien: Int,
    val namaPasien: String = "",
    val alamat: String = "",
    val noTelp: String= "",
    val tglLahir: String = "",
    val riwayatMedis: String = ""
)

fun PasInsertUiEvent.toPas(): Pasien = Pasien(
    idPasien = idPasien,
    namaPasien = namaPasien,
    alamat = alamat,
    noTelp = noTelp,
    tglLahir = tglLahir,
    riwayatMedis = riwayatMedis
)

fun Pasien.toUiStatePas(): PasInsertUiState = PasInsertUiState(
    pasInsertUiEvent = toPasInsertUiEvent()
)

fun Pasien.toPasInsertUiEvent(): PasInsertUiEvent = PasInsertUiEvent(
    idPasien = idPasien,
    namaPasien = namaPasien,
    alamat = alamat,
    noTelp = noTelp,
    tglLahir = tglLahir,
    riwayatMedis = riwayatMedis
)