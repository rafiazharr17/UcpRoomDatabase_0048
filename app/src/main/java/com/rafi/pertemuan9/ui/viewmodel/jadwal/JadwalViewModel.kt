package com.rafi.pertemuan9.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.pertemuan9.data.entity.Dokter
import com.rafi.pertemuan9.data.entity.Jadwal
import com.rafi.pertemuan9.repository.RepositoryJadwal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class JadwalViewModel(
    private val repositoryJadwal: RepositoryJadwal
): ViewModel(){
    var uiState by mutableStateOf(JadwalUIState())

    fun UpdateState(jadwalEvent: JadwalEvent){
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    fun validateFields(): Boolean {
        val event = uiState.jadwalEvent
        val errorState = FormJadwalErrorState(
            dokter = if (event.dokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHpPasien = if (event.noHpPasien.isNotEmpty()) null else "Nomor Hp Pasien tidak boleh kosong",
            tanggal = if (event.tanggal.isNotEmpty()) null else "Tanggal tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.jadwalEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryJadwal.insertJadwal(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormJadwalErrorState()
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

data class JadwalEvent(
    val idJadwal: Int = 0,
    val dokter: String = "",
    val namaPasien: String = "",
    val noHpPasien: String = "",
    val tanggal: String = "",
    val status: String = "",
)

fun JadwalEvent.toJadwalEntity(): Jadwal = Jadwal(
    idJadwal = idJadwal,
    dokter = dokter,
    namaPasien = namaPasien,
    noHpPasien = noHpPasien,
    tanggal = tanggal,
    status = status
)

data class FormJadwalErrorState(
    val dokter: String? = null,
    val namaPasien: String? = null,
    val noHpPasien: String? = null,
    val tanggal: String? = null,
    val status: String? = null,
){
    fun isValid(): Boolean {
        return dokter == null
                && namaPasien == null
                && noHpPasien == null
                && tanggal == null
                && status == null
    }
}

data class JadwalUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormJadwalErrorState = FormJadwalErrorState(),
    val snackBarMessage: String? = null
)