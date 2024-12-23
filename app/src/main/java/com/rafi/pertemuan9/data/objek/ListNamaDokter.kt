package com.rafi.pertemuan9.data.objek

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.rafi.pertemuan9.ui.viewmodel.dokter.HomeDokterViewModel

object ListNamaDokter {
    @Composable
    fun opsiPilihDokter(
        homeDokterViewModel: HomeDokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val dataNama by homeDokterViewModel.homeDokterUiState.collectAsState()
        val namaDokter = dataNama.listDokter.map { it.namaDokter }
        return namaDokter
    }
}