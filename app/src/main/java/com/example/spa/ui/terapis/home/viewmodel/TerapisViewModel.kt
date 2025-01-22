package com.example.spa.ui.terapis.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.Terapis
import com.example.spa.repository.TerapisRepository
import kotlinx.coroutines.launch

sealed class TerapisHomeUiState{
    data class Success(val terapis: List<Terapis>): TerapisHomeUiState()
    object Error : TerapisHomeUiState()
    object Loading : TerapisHomeUiState()
}

class TerapisHomeViewModel(private val ter: TerapisRepository): ViewModel(){
    var terUiState: TerapisHomeUiState by mutableStateOf(TerapisHomeUiState.Loading)
        private set

    init {
        getTer()
    }

    fun getTer(){
        viewModelScope.launch {
            terUiState = TerapisHomeUiState.Loading
            terUiState = try{
                TerapisHomeUiState.Success(ter.getTerapis().data)
            }catch (e: Exception){
                TerapisHomeUiState.Error
            }catch (e: Exception){
                TerapisHomeUiState.Error
            }
        }
    }
}