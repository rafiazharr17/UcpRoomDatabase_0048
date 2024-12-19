package com.rafi.pertemuan9.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rafi.pertemuan9.JadwalDokterApp
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                jadwalDokterApp().containerApp.repositoryDokter
            )
        }
    }
}

fun CreationExtras.jadwalDokterApp(): JadwalDokterApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JadwalDokterApp)