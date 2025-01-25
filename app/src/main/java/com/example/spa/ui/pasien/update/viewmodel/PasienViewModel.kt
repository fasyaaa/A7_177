package com.example.spa.ui.pasien.update.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.repository.PasienRepository
import com.example.spa.ui.pasien.insert.viewmodel.PasInsertUiEvent
import com.example.spa.ui.pasien.insert.viewmodel.PasInsertUiState
import com.example.spa.ui.pasien.insert.viewmodel.toPas
import com.example.spa.ui.pasien.insert.viewmodel.toUiStatePas
import kotlinx.coroutines.launch

class PasienUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val pas: PasienRepository
): ViewModel(){
    var pasienUpdateUiState by mutableStateOf(PasInsertUiState())
        private set

    private val _id_pasien: Int = checkNotNull(savedStateHandle[DestinasiUpdate.IdPasien])

    init {
        viewModelScope.launch {
            pasienUpdateUiState = pas.getPasienbyIdPasien(_id_pasien)
                .toUiStatePas()
        }
    }

    fun updateInsertPasState(insertUiState: PasInsertUiEvent){
        pasienUpdateUiState = PasInsertUiState(pasInsertUiEvent = insertUiState)
    }

    suspend fun updatePas(){
        viewModelScope.launch {
            try {
                pas.updatePasien(_id_pasien, pasienUpdateUiState.pasInsertUiEvent.toPas())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}