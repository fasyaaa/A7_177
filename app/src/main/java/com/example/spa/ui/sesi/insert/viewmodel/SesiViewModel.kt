package com.example.spa.ui.sesi.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.JenisTrapi
import com.example.spa.model.Pasien
import com.example.spa.model.Sesi
import com.example.spa.model.Terapis
import com.example.spa.repository.JenisTrapiRepository
import com.example.spa.repository.PasienRepository
import com.example.spa.repository.SesiRepository
import com.example.spa.repository.TerapisRepository
import com.example.spa.ui.pasien.insert.viewmodel.FormErrorStatePas
import kotlinx.coroutines.launch

class SesiInsertViewModel(private val ss: SesiRepository, private val pas: PasienRepository,
                          private val tps: TerapisRepository, private val JeT: JenisTrapiRepository)
    : ViewModel(){
    var sesiUiState by mutableStateOf(SesiInsertUiState())
        private set

    var pasienList by mutableStateOf<List<Pasien>>(emptyList())
        private set

    var terapisList by mutableStateOf<List<Terapis>>(emptyList())
        private set

    var jenisTrapiList by mutableStateOf<List<JenisTrapi>>(emptyList())
        private set

    init {
        getAllPasien()
        getAllTerapis()
        getAllJenisTrapi()
    }

    fun updateInsertSsState(sesiInsertUiEvent: SesiInsertUiEvent) {
        sesiUiState = SesiInsertUiState(sesiInsertUiEvent = sesiInsertUiEvent)
    }

    suspend fun insertSs() {
        viewModelScope.launch {
            try {
                ss.insertSesi(sesiUiState.sesiInsertUiEvent.toSs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun validateFields(insertUiEvent: SesiInsertUiEvent): FormErrorStateSesi {
        return FormErrorStateSesi(
            idPasienError = if (insertUiEvent.idPasien != null && insertUiEvent.idPasien > 0) null else "Pasien tidak boleh kosong",
            idTerapisError = if (insertUiEvent.idTerapis != null && insertUiEvent.idTerapis > 0) null else "Terapis tidak boleh kosong",
            idJenisTrapiError = if (insertUiEvent.idJenisTrapi != null && insertUiEvent.idJenisTrapi > 0) null else "Jenis Terapi tidak boleh kosong",
            catatanSesiError = if (insertUiEvent.catatanSesi.isNotEmpty()) null else "Catatan Sesi tidak boleh kosong",
            tanggalSesiError = if (insertUiEvent.tanggalSesi.isNotEmpty()) null else "Tanggal tidak boleh kosong"
        )
    }

    data class FormErrorStateSesi(
        val idPasienError: String? = null,
        val idTerapisError: String? = null,
        val idJenisTrapiError: String? = null,
        val catatanSesiError: String? = null,
        val tanggalSesiError: String? = null
    ) {
        fun isValid(): Boolean {
            return idPasienError == null &&
                    idTerapisError == null &&
                    idJenisTrapiError == null &&
                    catatanSesiError == null &&
                    tanggalSesiError == null
        }
    }

    private fun getAllPasien() {
        viewModelScope.launch {
            try {
                val response = pas.getPasien()
                pasienList = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun getAllTerapis() {
        viewModelScope.launch {
            try {
                val response = tps.getTerapis()
                terapisList = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun getAllJenisTrapi() {
        viewModelScope.launch {
            try {
                val response = JeT.getJenisTrapi()
                jenisTrapiList = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class SesiInsertUiState(
    val sesiInsertUiEvent: SesiInsertUiEvent = SesiInsertUiEvent()
)

data class SesiInsertUiEvent(
    val idSesi: Int? = null,
    val idPasien: Int? = null,
    val idTerapis: Int? = null,
    val idJenisTrapi: Int? = null,
    val tanggalSesi: String = "",
    val catatanSesi: String = ""
)

fun SesiInsertUiEvent.toSs(): Sesi = Sesi(
    idSesi = idSesi ?: 0,
    idPasien = idPasien ?: 0,
    idTerapis = idTerapis ?: 0,
    idJenisTrapi = idJenisTrapi ?: 0,
    tanggalSesi = tanggalSesi,
    catatanSesi = catatanSesi,
    namaPasien = "",
    namaTerapis = "",
    namaJenisTrapi = ""
)

fun Sesi.toUiStateSs(): SesiInsertUiState = SesiInsertUiState(
    sesiInsertUiEvent = toSsInsertUiEvent()
)

fun Sesi.toSsInsertUiEvent(): SesiInsertUiEvent = SesiInsertUiEvent(
    idSesi = idSesi,
    idPasien = idPasien,
    idTerapis = idTerapis,
    idJenisTrapi = idJenisTrapi,
    tanggalSesi = tanggalSesi,
    catatanSesi = catatanSesi
)