package com.example.spa.ui.jenisTrapi.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.spa.model.JenisTrapi
import com.example.spa.repository.JenisTrapiRepository
import com.example.spa.ui.pasien.detail.viewmodel.PasienDetailUiState
import kotlinx.coroutines.launch
import okio.IOException

sealed class JenisTrapiDetailUiState{
    data class Success(val jenisTrapi: JenisTrapi): JenisTrapiDetailUiState()
    object Error: JenisTrapiDetailUiState()
    object Loading: JenisTrapiDetailUiState()
}

class JenisTrapiDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val jtr: JenisTrapiRepository
): ViewModel(){
    var jenisTrapiDetailState: JenisTrapiDetailUiState by mutableStateOf(JenisTrapiDetailUiState.Loading)
        private set

    private val _id_jenisTrapi: Int = checkNotNull(savedStateHandle[DestinasiJenisTrapiDetail.IdJenisTrapi])

    init {
        getJenisTrapibyIdJenisTrapi()
    }

    fun getJenisTrapibyIdJenisTrapi(){
        viewModelScope.launch {
            jenisTrapiDetailState = JenisTrapiDetailUiState.Loading
            jenisTrapiDetailState = try {
                val jenisTrapi = jtr.getJenisTrapibyIdJenisTrapi(_id_jenisTrapi)
                JenisTrapiDetailUiState.Success(jenisTrapi)
            }catch (e: IOException){
                JenisTrapiDetailUiState.Error
            }catch (e: HttpException){
                JenisTrapiDetailUiState.Error
            }
        }
    }

    fun deleteJeT(idJenisTrapi: Int){
        viewModelScope.launch {
            try {
                jtr.deleteJenisTrapi(idJenisTrapi)
            }catch (e: Exception){
                JenisTrapiDetailUiState.Error
            }catch (e: HttpException){
                JenisTrapiDetailUiState.Error
            }
        }
    }
}