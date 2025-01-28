package com.example.spa.ui.sesi.update.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.repository.SesiRepository
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertUiEvent
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertUiState
import com.example.spa.ui.sesi.insert.viewmodel.toSs
import com.example.spa.ui.sesi.insert.viewmodel.toUiStateSs
import com.example.spa.ui.sesi.update.page.DestinasiUpdateSs
import kotlinx.coroutines.launch

class SesiUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val ss: SesiRepository
) : ViewModel() {

    // State untuk menyimpan data sesi
    var sesiUpdateUiState by mutableStateOf(SesiInsertUiState())
        private set

    // Mendapatkan ID sesi dari SavedStateHandle
    private val _id_sesi: Int = checkNotNull(savedStateHandle[DestinasiUpdateSs.idSesi])

    init {
        viewModelScope.launch {
            // Mendapatkan data sesi berdasarkan ID dan mengonversi ke UI state
            sesiUpdateUiState = ss.getSesibyIdSesi(_id_sesi).toUiStateSs()
        }
    }

    // Memperbarui UI state berdasarkan event input pengguna
    fun updateInsertSsState(sesiInsertUiEvent: SesiInsertUiEvent) {
        sesiUpdateUiState = sesiUpdateUiState.copy(sesiInsertUiEvent = sesiInsertUiEvent)
    }

    // Fungsi untuk melakukan update sesi
    suspend fun updateSs() {
        viewModelScope.launch {
            try {
                ss.updateSesi(_id_sesi, sesiUpdateUiState.sesiInsertUiEvent.toSs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
