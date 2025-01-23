package com.example.spa.ui.pasien

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spa.SpaApplications
import com.example.spa.ui.pasien.detail.viewmodel.PasienDetailViewModel
import com.example.spa.ui.pasien.home.viewmodel.PasienHomeViewModel
import com.example.spa.ui.pasien.insert.viewmodel.PasienInsertViewModel
import com.example.spa.ui.pasien.update.viewmodel.PasienUpdateViewModel

object PasienPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { PasienHomeViewModel(SpaApplications().PasienContainer.kontakPasienRepository) }
        initializer { PasienInsertViewModel(SpaApplications().PasienContainer.kontakPasienRepository ) }
        initializer { PasienDetailViewModel(createSavedStateHandle(),SpaApplications().PasienContainer.kontakPasienRepository) }
        initializer { PasienUpdateViewModel(createSavedStateHandle(),SpaApplications().PasienContainer.kontakPasienRepository) }
    }
}

fun CreationExtras.SpaApplications(): SpaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaApplications)