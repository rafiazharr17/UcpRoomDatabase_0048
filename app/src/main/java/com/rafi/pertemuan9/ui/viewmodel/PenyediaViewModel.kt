package com.rafi.pertemuan9.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rafi.pertemuan9.JadwalDokterApp
import com.rafi.pertemuan9.ui.view.dokter.HomeDokterView
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterViewModel
import com.rafi.pertemuan9.ui.viewmodel.dokter.HomeDokterViewModel
import com.rafi.pertemuan9.ui.viewmodel.jadwal.HomeJadwalViewModel
import com.rafi.pertemuan9.ui.viewmodel.jadwal.JadwalViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                jadwalDokterApp().containerApp.repositoryDokter
            )
        }

        initializer {
            HomeDokterViewModel(
                jadwalDokterApp().containerApp.repositoryDokter
            )
        }

        initializer {
            JadwalViewModel(
                jadwalDokterApp().containerApp.repositoryJadwal
            )
        }

        initializer {
            HomeJadwalViewModel(
                jadwalDokterApp().containerApp.repositoryJadwal
            )
        }
    }
}

fun CreationExtras.jadwalDokterApp(): JadwalDokterApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JadwalDokterApp)