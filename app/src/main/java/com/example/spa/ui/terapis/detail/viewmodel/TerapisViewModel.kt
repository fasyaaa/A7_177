package com.example.spa.ui.terapis.detail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.spa.model.Terapis
import com.example.spa.repository.TerapisRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class TerapisDetailUiState{
    data class Success(val terapis: Terapis) : TerapisDetailUiState()
    object Error: TerapisDetailUiState()
    object Loading: TerapisDetailUiState()
}

class TerapisDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val ter: TerapisRepository
) : ViewModel(){
    var terapisDetailUiState: TerapisDetailUiState by mutableStateOf(TerapisDetailUiState.Loading)
        private set

    private val _id_terapis: Int = checkNotNull(savedStateHandle[DestinasiTerapisDetail.IdTerapis])

    init {
        getTerapisbyIdTerapis()
    }

    fun getTerapisbyIdTerapis(){
        viewModelScope.launch {
            terapisDetailUiState = TerapisDetailUiState.Loading
            terapisDetailUiState = try {
                val terapis = ter.getTerapisbyIdTerapis(_id_terapis)
                TerapisDetailUiState.Success(terapis)
            } catch (e: IOException){
                TerapisDetailUiState.Error
            } catch (e: HttpException){
                TerapisDetailUiState.Error
            }
        }
    }

    fun deleteTer(id_terapis: Int){
        viewModelScope.launch {
            try {
                ter.deleteTerapis(id_terapis)
            } catch (e: IOException){
                TerapisDetailUiState.Error
            } catch (e: HttpException){
                TerapisDetailUiState.Error
            }
        }
    }
}