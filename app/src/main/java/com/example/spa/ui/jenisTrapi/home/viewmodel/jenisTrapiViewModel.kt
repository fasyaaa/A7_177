package com.example.spa.ui.jenisTrapi.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spa.model.JenisTrapi
import com.example.spa.repository.JenisTrapiRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class JenisTrapiHomeUiState{
    data class Success(val jenisTrapi: List<JenisTrapi>): JenisTrapiHomeUiState()
    object Error: JenisTrapiHomeUiState()
    object Loading: JenisTrapiHomeUiState()
}

class JenisTrapiHomeViewModel(private val jtr: JenisTrapiRepository): ViewModel(){
    var jtrUiState: JenisTrapiHomeUiState by mutableStateOf(JenisTrapiHomeUiState.Loading)
        private set

    init {
        getJtr()
    }

    fun getJtr(){
        viewModelScope.launch {
            jtrUiState = JenisTrapiHomeUiState.Loading
            jtrUiState = try {
                JenisTrapiHomeUiState.Success(jtr.getJenisTrapi().data)
            } catch (e: IOException){
                JenisTrapiHomeUiState.Error
            } catch (e: IOException){
                JenisTrapiHomeUiState.Error
            }
        }
    }
}