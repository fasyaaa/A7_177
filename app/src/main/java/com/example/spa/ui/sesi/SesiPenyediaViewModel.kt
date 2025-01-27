package com.example.spa.ui.sesi

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spa.SpaApplications
import com.example.spa.ui.sesi.detail.viewmodel.SesiDetailViewModel
import com.example.spa.ui.sesi.home.viewmodel.SesiHomeViewModel
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertViewModel
import com.example.spa.ui.sesi.update.viewmodel.SesiUpdateViewModel

object SesiPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { SesiHomeViewModel(SpaApplications().SesiContainer.kontakSesiRepository) }
        initializer { SesiInsertViewModel(SpaApplications().SesiContainer.kontakSesiRepository ) }
        initializer { SesiDetailViewModel(createSavedStateHandle(),SpaApplications().SesiContainer.kontakSesiRepository) }
        initializer { SesiUpdateViewModel(createSavedStateHandle(),SpaApplications().SesiContainer.kontakSesiRepository) }
    }
}

fun CreationExtras.SpaApplications(): SpaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaApplications)