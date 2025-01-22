package com.example.spa.ui.terapis.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.Terapis
import com.example.spa.repository.TerapisRepository
import kotlinx.coroutines.launch

class TerapisViewModel(private val ter: TerapisRepository): ViewModel(){
    var terapisInsertUiState by mutableStateOf(TerapisInsertUiState())
        private set

    fun terapisUpdateInsertState(terapisInsertUiEvent: TerapisInsertUiEvent){
        terapisInsertUiState = TerapisInsertUiState(terapisInsertUiEvent = terapisInsertUiEvent)
    }

    suspend fun insertTer(){
        viewModelScope.launch {
            try {
                ter.insertTerapis(terapisInsertUiState.terapisInsertUiEvent.toTer())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class TerapisInsertUiState(
    val terapisInsertUiEvent: TerapisInsertUiEvent = TerapisInsertUiEvent()
)

data class TerapisInsertUiEvent(
    val id_terapis: String = "",
    val nama_terapis: String = "",
    val spesialis: String = "",
    val no_izin_prak: String = ""
)

fun TerapisInsertUiEvent.toTer(): Terapis = Terapis(
    id_terapis = id_terapis,
    nama_terapis = nama_terapis,
    spesialis = spesialis,
    no_izin_prak = no_izin_prak
)

fun Terapis.toUiStateTer(): TerapisInsertUiState = TerapisInsertUiState(
    terapisInsertUiEvent = toTerapisInsertUiEvent()
)

fun Terapis.toTerapisInsertUiEvent(): TerapisInsertUiEvent = TerapisInsertUiEvent(
    id_terapis = id_terapis,
    nama_terapis = nama_terapis,
    spesialis = spesialis,
    no_izin_prak = no_izin_prak
)