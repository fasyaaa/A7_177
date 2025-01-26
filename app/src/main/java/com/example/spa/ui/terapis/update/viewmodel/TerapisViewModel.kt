package com.example.spa.ui.terapis.update.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.repository.TerapisRepository
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertUiEvent
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertUiState
import com.example.spa.ui.terapis.insert.viewmodel.toTer
import com.example.spa.ui.terapis.insert.viewmodel.toUiStateTer
import kotlinx.coroutines.launch

class TerapisUpdateViewModel (
    savedStateHandle: SavedStateHandle,
    private val ter: TerapisRepository
): ViewModel(){
    var terapisUpdateUiState by mutableStateOf(TerapisInsertUiState())
        private set

    private val _id_terapis: Int = checkNotNull(savedStateHandle[DestinasiTerapisUpdate.IdTerapis])
    init {
        viewModelScope.launch {
            terapisUpdateUiState = ter.getTerapisbyIdTerapis(_id_terapis)
                .toUiStateTer()
        }
    }

    fun updateInsertTerState(terapisInsertUiEvent: TerapisInsertUiEvent){
        terapisUpdateUiState = TerapisInsertUiState(terapisInsertUiEvent = terapisInsertUiEvent)
    }

    suspend fun updateTer(){
        viewModelScope.launch {
            try {
                ter.updateTerapis(_id_terapis, terapisUpdateUiState.terapisInsertUiEvent.toTer())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}