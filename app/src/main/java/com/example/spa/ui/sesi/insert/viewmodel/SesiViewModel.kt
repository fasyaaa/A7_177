package com.example.spa.ui.sesi.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.JenisTrapi
import com.example.spa.model.Pasien
import com.example.spa.model.Sesi
import com.example.spa.model.Terapis
import com.example.spa.repository.SesiRepository
import kotlinx.coroutines.launch
import java.sql.Date

class SesiInsertViewModel(private val ss: SesiRepository): ViewModel(){
    var sesiUiState by mutableStateOf(SesiInsertUiState())
        private set

    fun updateInsertSsState(sesiInsertUiEvent: SesiInsertUiEvent){
        sesiUiState = SesiInsertUiState(sesiInsertUiEvent = sesiInsertUiEvent)
    }

    suspend fun insertSs(){
        viewModelScope.launch {
            try {
                ss.insertSesi(sesiUiState.sesiInsertUiEvent.toSs())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class SesiInsertUiState(
    val sesiInsertUiEvent: SesiInsertUiEvent = SesiInsertUiEvent(0, 0, 0, 0, "", "")
)

data class SesiInsertUiEvent(
    val idSesi: Int,
    val idPasien: Int,
    val idTerapis: Int,
    val idJenisTrapi: Int,
    val tanggalSesi: String,
    val catatanSesi: String
)

fun SesiInsertUiEvent.toSs(): Sesi = Sesi(
    idSesi = idSesi,
    idPasien = idPasien,
    idTerapis = idTerapis,
    idJenisTrapi = idJenisTrapi,
    tanggalSesi = tanggalSesi,
    catatanSesi = catatanSesi
)

fun Sesi.toUiStateSs(): SesiInsertUiState = SesiInsertUiState(
    sesiInsertUiEvent = toSsInsertUiEvent()
)

fun Sesi.toSsInsertUiEvent(): SesiInsertUiEvent = SesiInsertUiEvent(
    idSesi = idSesi,
    idPasien = idPasien,
    idTerapis = idTerapis,
    idJenisTrapi = idJenisTrapi,
    tanggalSesi = tanggalSesi,
    catatanSesi = catatanSesi
)