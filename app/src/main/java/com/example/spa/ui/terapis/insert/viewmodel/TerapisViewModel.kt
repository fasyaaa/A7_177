package com.example.spa.ui.terapis.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.Terapis
import com.example.spa.repository.TerapisRepository
import com.example.spa.ui.pasien.insert.viewmodel.FormErrorStatePas
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

    fun validateFields(): Boolean {
        val event = terapisInsertUiState.terapisInsertUiEvent
        val errorState = FormErrorStateTps(
            idTerapis = if (event.idTerapis != null && event.idTerapis > 0) null else "ID Terapis tidak boleh kosong",
            namaTerapis = if (event.namaTerapis.isNotEmpty()) null else "Nama Terapis tidak boleh kosong",
            spesialis = if (event.spesialis.isNotEmpty()) null else "Spesialis tidak boleh kosong",
            noIzinPrak = if (event.noIzinPrak.isNotEmpty()) null else "Spesialis tidak boleh kosong"
            )
        terapisInsertUiState = terapisInsertUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
}

data class TerapisInsertUiState(
    val terapisInsertUiEvent: TerapisInsertUiEvent = TerapisInsertUiEvent(),
    val isEntryValid: FormErrorStateTps = FormErrorStateTps(),
)

data class FormErrorStateTps(
    val idTerapis: String? = null,
    val namaTerapis: String? = null,
    val spesialis: String? = null,
    val noIzinPrak: String? = null
    ){
    fun isValid(): Boolean{
        return idTerapis == null &&
                namaTerapis == null &&
                spesialis == null &&
                noIzinPrak == null
    }
    fun hasError(field: String?): Boolean = field != null
}

data class TerapisInsertUiEvent(
    val idTerapis: Int? = null,
    val namaTerapis: String = "",
    val spesialis: String = "",
    val noIzinPrak: String = ""
)

fun TerapisInsertUiEvent.toTer(): Terapis = Terapis(
    idTerapis = idTerapis ?: 0,
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