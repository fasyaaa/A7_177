package com.example.spa.ui.sesi.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.Sesi
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
    val sesiInsertUiEvent: SesiInsertUiEvent = SesiInsertUiEvent()
)

data class SesiInsertUiEvent(
    val id_sesi: String = "",
    val id_pasien: String = "",
    val id_terapis: String = "",
    val id_jenisTrapi: String = "",
    val tanggal_sesi: Date = Date(System.currentTimeMillis()),
    val catatan_sesi: String = ""
)

fun SesiInsertUiEvent.toSs(): Sesi = Sesi(
    id_sesi = id_sesi,
    id_pasien = id_pasien,
    id_terapis = id_terapis,
    id_jenisTrapi = id_jenisTrapi,
    tanggal_sesi = tanggal_sesi,
    catatan_sesi = catatan_sesi
)

fun Sesi.toUiStateSs(): SesiInsertUiState = SesiInsertUiState(
    sesiInsertUiEvent = toSsInsertUiEvent()
)

fun Sesi.toSsInsertUiEvent(): SesiInsertUiEvent = SesiInsertUiEvent(
    id_sesi = id_sesi,
    id_pasien = id_pasien,
    id_terapis = id_terapis,
    id_jenisTrapi = id_jenisTrapi,
    tanggal_sesi = tanggal_sesi,
    catatan_sesi = catatan_sesi
)