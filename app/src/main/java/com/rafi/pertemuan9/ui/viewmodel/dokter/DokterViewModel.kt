package com.rafi.pertemuan9.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.pertemuan9.data.entity.Dokter
import com.rafi.pertemuan9.repository.RepositoryDokter
import kotlinx.coroutines.launch

class DokterViewModel(
    private val repositoryDokter: RepositoryDokter
): ViewModel(){
    var uiState by mutableStateOf(DokterUIState())

    fun UpdateState(dokterEvent: DokterEvent){
        uiState = uiState.copy(
            dokterEvent = dokterEvent
        )
    }

    fun validateFields(): Boolean {
        val event = uiState.dokterEvent
        val errorState = FormErrorState(
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            spesialis = if (event.spesialis.isNotEmpty()) null else "Spesialis tidak boleh kosong",
            klinik = if (event.klinik.isNotEmpty()) null else "Klinik tidak boleh kosong",
            nomorHpDokter = if (event.nomorHpDokter.isNotEmpty()) null else "Nomor Hp Dokter tidak boleh kosong",
            jamKerja = if (event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.dokterEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryDokter.insertDokter(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dokterEvent = DokterEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }

    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class DokterEvent(
    val idDokter: Int = 0,
    val namaDokter: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val nomorHpDokter: String = "",
    val jamKerja: String = "",
)

fun DokterEvent.toDokterEntity(): Dokter = Dokter(
    idDokter = idDokter,
    namaDokter = namaDokter,
    spesialis = spesialis,
    klinik = klinik,
    nomorHpDokter = nomorHpDokter,
    jamKerja = jamKerja
)

data class FormErrorState(
    val namaDokter: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val nomorHpDokter: String? = null,
    val jamKerja: String? = null,
){
    fun isValid(): Boolean {
        return namaDokter == null
                && spesialis == null
                && klinik == null
                && nomorHpDokter == null
                && jamKerja == null
    }
}

data class DokterUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)