package com.example.spa.ui.terapis

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spa.SpaApplications
import com.example.spa.ui.terapis.detail.viewmodel.TerapisDetailViewModel
import com.example.spa.ui.terapis.home.viewmodel.TerapisHomeViewModel
import com.example.spa.ui.terapis.insert.viewmodel.TerapisInsertViewModel
import com.example.spa.ui.terapis.update.viewmodel.TerapisUpdateViewModel

object TerapisPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { TerapisHomeViewModel(SpaApplications().TerapisContainer.kontakTerapisRepository) }
        initializer { TerapisInsertViewModel(SpaApplications().TerapisContainer.kontakTerapisRepository) }
        initializer { TerapisDetailViewModel(createSavedStateHandle(),SpaApplications().TerapisContainer.kontakTerapisRepository) }
        initializer { TerapisUpdateViewModel(createSavedStateHandle(),SpaApplications().TerapisContainer.kontakTerapisRepository) }
    }
}

fun CreationExtras.SpaApplications(): SpaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaApplications)