package com.example.spa.ui.pasien.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.spa.model.Pasien
import com.example.spa.repository.PasienRepository
import com.example.spa.ui.pasien.detail.page.DestinasiDetailPasien
import kotlinx.coroutines.launch

sealed class PasienDetailUiState {
    data class Success(val pasien: Pasien) : PasienDetailUiState()
    object Error: PasienDetailUiState()
    object Loading: PasienDetailUiState()
}

class PasienDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pas: PasienRepository
) : ViewModel(){
    var pasienDetailState: PasienDetailUiState by mutableStateOf(PasienDetailUiState.Loading)
        private set

    private val _id_pasien: Int = checkNotNull(savedStateHandle[DestinasiDetailPasien.idPasien])

    init {
        getPasienbyIdPasien()
    }

    fun getPasienbyIdPasien(){
        viewModelScope.launch {
            pasienDetailState = PasienDetailUiState.Loading
            pasienDetailState = try {
                val pasien = pas.getPasienbyIdPasien(_id_pasien)
                PasienDetailUiState.Success(pasien)
            }catch (e: Exception){
                PasienDetailUiState.Error
            }catch (e: Exception){
                PasienDetailUiState.Error
            }
        }
    }

    fun deletePas(idPasien: Int){
        viewModelScope.launch {
            try {
                pas.deletePasien(idPasien)
            }catch (e: Exception){
                PasienDetailUiState.Error
            }catch (e: HttpException){
                PasienDetailUiState.Error
            }
        }
    }
}