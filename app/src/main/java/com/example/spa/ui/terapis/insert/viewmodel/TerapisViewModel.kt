package com.example.spa.ui.terapis.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.Terapis
import com.example.spa.repository.TerapisRepository
import kotlinx.coroutines.launch

class TerapisInsertViewModel(private val ter: TerapisRepository): ViewModel(){
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
    val terapisInsertUiEvent: TerapisInsertUiEvent = TerapisInsertUiEvent( 0, "", "", "")
)

data class TerapisInsertUiEvent(
    val idTerapis: Int,
    val namaTerapis: String = "",
    val spesialis: String = "",
    val noIzinPrak: String = ""
)

fun TerapisInsertUiEvent.toTer(): Terapis = Terapis(
    idTerapis = idTerapis,
    namaTerapis = namaTerapis,
    spesialis = spesialis,
    noIzinPrak = noIzinPrak
)

fun Terapis.toUiStateTer(): TerapisInsertUiState = TerapisInsertUiState(
    terapisInsertUiEvent = toTerapisInsertUiEvent()
)

fun Terapis.toTerapisInsertUiEvent(): TerapisInsertUiEvent = TerapisInsertUiEvent(
    idTerapis = idTerapis,
    namaTerapis = namaTerapis,
    spesialis = spesialis,
    noIzinPrak = noIzinPrak
)