package com.example.spa.ui.jenisTrapi.update.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.repository.JenisTrapiRepository
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertUiEvent
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertUiState
import com.example.spa.ui.jenisTrapi.insert.viewmodel.toJtr
import com.example.spa.ui.jenisTrapi.insert.viewmodel.toUiStateJtr
import kotlinx.coroutines.launch

class JenisTrapiUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val jtr: JenisTrapiRepository
): ViewModel(){
    var jenisTrapiUpdateUiState by mutableStateOf(JenisTrapiInsertUiState())
        private set

    private val _id_jenisTrapi: Int = checkNotNull(savedStateHandle[DestinasiJenisTrapiUpdate.IdJenisTrapi])

    init {
        viewModelScope.launch {
            jenisTrapiUpdateUiState = jtr.getJenisTrapibyIdJenisTrapi(_id_jenisTrapi)
                .toUiStateJtr()
        }
    }

    fun updateInsertJtrState(jenisTrapiInsertUiEvent: JenisTrapiInsertUiEvent){
        jenisTrapiUpdateUiState = JenisTrapiInsertUiState(jenisTrapiInsertUiEvent = jenisTrapiInsertUiEvent)
    }

    suspend fun updateJtr(){
        viewModelScope.launch {
            try {
                jtr.updateJenisTrapi(_id_jenisTrapi, jenisTrapiUpdateUiState.jenisTrapiInsertUiEvent.toJtr())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}