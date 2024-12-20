package com.rafi.pertemuan9.ui.viewmodel.dokter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.pertemuan9.data.entity.Dokter
import com.rafi.pertemuan9.repository.RepositoryDokter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDokterViewModel(
    private val repositoryDokter: RepositoryDokter
) : ViewModel() {
    val homeDokterUiState: StateFlow<HomeDokterUiState> = repositoryDokter.getAllDokter()
        .filterNotNull()
        .map {
            HomeDokterUiState(
                listDokter = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeDokterUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeDokterUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message?: "Terjadi kesalah"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeDokterUiState(
                isLoading = true
            )
        )
}

data class HomeDokterUiState(
    val listDokter: List<Dokter> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)