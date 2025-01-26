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
import kotlinx.coroutines.launch

class SesiUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val ss: SesiRepository
) : ViewModel(){
    var sesiUpdateUiState by mutableStateOf(SesiInsertUiState())
        private set

    private val _id_sesi: Int = checkNotNull(savedStateHandle[DestinasiSesiUpdate.IdSesi])

    init {
        viewModelScope.launch {
            sesiUpdateUiState = ss.getSesibyIdSesi(_id_sesi)
                .toUiStateSs()
        }
    }

    fun updateInsertSsState(sesiInsertUiEvent: SesiInsertUiEvent){
        sesiUpdateUiState = SesiInsertUiState(sesiInsertUiEvent = sesiInsertUiEvent)
    }

    suspend fun updateSs(){
        viewModelScope.launch {
            try {
                ss.updateSesi(_id_sesi, sesiUpdateUiState.sesiInsertUiEvent.toSs())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}