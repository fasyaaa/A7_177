package com.example.spa.ui.jenisTrapi.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.spa.model.JenisTrapi
import com.example.spa.repository.JenisTrapiRepository

class JenisTrapiInsertViewModel(private val jtr: JenisTrapiRepository): ViewModel(){
    var jenisTrapisUiState by mutableStateOf(JenisTrapiInsertUiState())
        private set

    fun updateInsertJtrState(jenisTrapiInsertUiEvent: JenisTrapiInsertUiEvent){
        jenisTrapisUiState = JenisTrapiInsertUiState(jenisTrapiInsertUiEvent = jenisTrapiInsertUiEvent)
    }
}

data class JenisTrapiInsertUiState(
    val jenisTrapiInsertUiEvent: JenisTrapiInsertUiEvent = JenisTrapiInsertUiEvent()
)

data class JenisTrapiInsertUiEvent(
    val idJenisTrapi: Int = 0,
    val namaJenisTrapi: String = "",
    val deskripsiJenisTrapi: String = ""
)

fun JenisTrapiInsertUiEvent.toJtr(): JenisTrapi = JenisTrapi(
    idJenisTrapi = idJenisTrapi,
    namaJenisTrapi = namaJenisTrapi,
    deskripsiJenisTrapi = deskripsiJenisTrapi
)

fun JenisTrapi.toUiStateJtr(): JenisTrapiInsertUiState = JenisTrapiInsertUiState(
    jenisTrapiInsertUiEvent = toJenisTrapiEvent()
)

fun JenisTrapi.toJenisTrapiEvent(): JenisTrapiInsertUiEvent = JenisTrapiInsertUiEvent(
    idJenisTrapi = idJenisTrapi,
    namaJenisTrapi = namaJenisTrapi,
    deskripsiJenisTrapi = deskripsiJenisTrapi
)