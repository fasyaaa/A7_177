package com.example.spa.ui.sesi.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.spa.model.Sesi
import com.example.spa.repository.SesiRepository
import kotlinx.coroutines.launch

sealed class SesiHomeUiState{
    data class Success(val sesi: List<Sesi>): SesiHomeUiState()
    object Error : SesiHomeUiState()
    object Loading : SesiHomeUiState()
}

class SesiHomeViewModel (private val ss: SesiRepository)
    : ViewModel(){
    var ssUiState: SesiHomeUiState by mutableStateOf(SesiHomeUiState.Loading)
        private set

    init {
        getSs()
    }

    fun getSs(){
        viewModelScope.launch {
            ssUiState = SesiHomeUiState.Loading
            ssUiState = try {
                SesiHomeUiState.Success(ss.getSesi().data)
            } catch (e: Exception){
                SesiHomeUiState.Error
            } catch (e: HttpException){
                SesiHomeUiState.Error
            }
        }
    }
}