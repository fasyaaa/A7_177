package com.example.spa.ui.pasien.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.Pasien
import com.example.spa.repository.PasienRepository
import kotlinx.coroutines.launch

class PasienInsertViewModel(
    private val pas: PasienRepository
) : ViewModel(){
    var pasUiState by mutableStateOf(PasInsertUiState())
        private set

    fun updateInsertPasState(pasInsertUiEvent: PasInsertUiEvent){
        pasUiState = PasInsertUiState(pasInsertUiEvent = pasInsertUiEvent)
    }

    suspend fun insertPas(){
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pas.insertPasien(pasUiState.pasInsertUiEvent.toPas())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun validateFields(): Boolean {
        val event = pasUiState.pasInsertUiEvent
        val isNoTelpValid = event.noTelp.matches(Regex("^[0-9]+$"))
        val errorState = FormErrorStatePas(
            idPasien = if (event.idPasien != null && event.idPasien > 0) null else "ID Pasien tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            noTelp = if (event.noTelp.isNotEmpty()) {
                if (isNoTelpValid) null else "Nomor Telepon harus terdiri dari angka saja"
            } else {
                "Nomor Telepon tidak boleh kosong"
            },
            tglLahir = if (event.tglLahir.isNotEmpty()) null else "Tanggal Lahir tidak boleh kosong",
            riwayatMedis = if (event.riwayatMedis.isNotEmpty()) null else "Riwayat Medis tidak boleh kosong"
        )
        pasUiState = pasUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
}

data class PasInsertUiState(
    val pasInsertUiEvent: PasInsertUiEvent = PasInsertUiEvent(),
    val isEntryValid: FormErrorStatePas = FormErrorStatePas(),
)

data class FormErrorStatePas(
    val idPasien: String? = null,
    val namaPasien: String? = null,
    val alamat: String? = null,
    val noTelp: String? = null,
    val tglLahir: String? = null,
    val riwayatMedis: String? = null
){
    fun isValid(): Boolean{
        return idPasien == null &&
                namaPasien == null &&
                alamat == null &&
                noTelp == null &&
                tglLahir == null &&
                riwayatMedis == null
    }
    fun hasError(field: String?): Boolean = field != null
}

data class PasInsertUiEvent(
    val idPasien: Int? = null,
    val namaPasien: String = "",
    val alamat: String = "",
    val noTelp: String= "",
    val tglLahir: String = "",
    val riwayatMedis: String = ""
)

fun PasInsertUiEvent.toPas(): Pasien = Pasien(
    idPasien = idPasien ?: 0,
    namaPasien = namaPasien,
    alamat = alamat,
    noTelp = noTelp,
    tglLahir = tglLahir,
    riwayatMedis = riwayatMedis
)

fun Pasien.toUiStatePas(): PasInsertUiState = PasInsertUiState(
    pasInsertUiEvent = toPasInsertUiEvent()
)

fun Pasien.toPasInsertUiEvent(): PasInsertUiEvent = PasInsertUiEvent(
    idPasien = idPasien,
    namaPasien = namaPasien,
    alamat = alamat,
    noTelp = noTelp,
    tglLahir = tglLahir,
    riwayatMedis = riwayatMedis
)