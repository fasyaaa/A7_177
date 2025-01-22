package com.example.spa.ui.pasien.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.spa.model.Pasien
import com.example.spa.repository.PasienRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class PasienHomeUiState{
    data class Success(val pasien: List<Pasien>) : PasienHomeUiState()
    object Error: PasienHomeUiState()
    object Loading: PasienHomeUiState()
}

class PasienHomeViewModel(private val pas: PasienRepository) : ViewModel(){
    var pasUiState: PasienHomeUiState by mutableStateOf(PasienHomeUiState.Loading)
        private set

    init {
        getPas()
    }

    fun getPas(){
        viewModelScope.launch {
            pasUiState = PasienHomeUiState.Loading
            pasUiState = try {
                PasienHomeUiState.Success(pas.getPasien().data)
            }catch (e: IOException){
                PasienHomeUiState.Error
            } catch (e: HttpException){
                PasienHomeUiState.Error
            }
        }
    }
}