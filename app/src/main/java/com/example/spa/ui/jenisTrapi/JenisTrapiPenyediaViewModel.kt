package com.example.spa.ui.jenisTrapi

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spa.SpaApplications
import com.example.spa.ui.jenisTrapi.detail.viewmodel.JenisTrapiDetailViewModel
import com.example.spa.ui.jenisTrapi.home.viewmodel.JenisTrapiHomeViewModel
import com.example.spa.ui.jenisTrapi.insert.viewmodel.JenisTrapiInsertViewModel
import com.example.spa.ui.jenisTrapi.update.viewmodel.JenisTrapiUpdateViewModel

object JenisTrapiPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { JenisTrapiHomeViewModel(SpaApplications().JenisTrapiContainer.kontakJenisTrapiRepository) }
        initializer { JenisTrapiInsertViewModel (SpaApplications().JenisTrapiContainer.kontakJenisTrapiRepository ) }
        initializer { JenisTrapiDetailViewModel(createSavedStateHandle(),SpaApplications().JenisTrapiContainer.kontakJenisTrapiRepository) }
        initializer { JenisTrapiUpdateViewModel(createSavedStateHandle(),SpaApplications().JenisTrapiContainer.kontakJenisTrapiRepository) }
    }
}

fun CreationExtras.SpaApplications(): SpaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpaApplications)