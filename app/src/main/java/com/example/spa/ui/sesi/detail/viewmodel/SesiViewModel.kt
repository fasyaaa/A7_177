package com.example.spa.ui.sesi.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.spa.model.Sesi
import com.example.spa.repository.SesiRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class SesiDetailUiState{
    data class Success(val sesi: Sesi) : SesiDetailUiState()
    object Error : SesiDetailUiState()
    object Loading : SesiDetailUiState()
}

class SesiDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val ss: SesiRepository
) : ViewModel(){

    var sesiDetailUiState: SesiDetailUiState by mutableStateOf(SesiDetailUiState.Loading)
        private set

    private val _id_sesi: String = checkNotNull(savedStateHandle[DestinasiSesiDetail.IdSesi])

    init {
        getSesibyIdSesi()
    }

    fun getSesibyIdSesi(){
        viewModelScope.launch {
            sesiDetailUiState = SesiDetailUiState.Loading
            sesiDetailUiState = try {
                val sesi = ss.getSesibyIdSesi(_id_sesi)
                SesiDetailUiState.Success(sesi)
            }catch (e: IOException){
                SesiDetailUiState.Error
            }catch (e: HttpException){
                SesiDetailUiState.Error
            }
        }
    }

    fun deleteSs(idsesi: String){
        viewModelScope.launch {
            try {
                ss.deleteSesi(idsesi)
            }catch (e: IOException){
                SesiDetailUiState.Error
            }catch (e: HttpException){
                SesiDetailUiState.Error
            }
        }
    }
}