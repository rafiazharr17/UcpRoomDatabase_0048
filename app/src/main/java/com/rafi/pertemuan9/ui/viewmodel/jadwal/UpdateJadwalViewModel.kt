package com.rafi.pertemuan9.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.pertemuan9.data.entity.Jadwal
import com.rafi.pertemuan9.repository.RepositoryJadwal
import com.rafi.pertemuan9.ui.navigation.DestinasiDetailJadwal
import com.rafi.pertemuan9.ui.navigation.DestinasiUpdateJadwal
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwal: RepositoryJadwal
) : ViewModel() {
    var updateJadwalUIState by mutableStateOf(JadwalUIState())
        private set

    private val _idJadwal: Int = checkNotNull(savedStateHandle[DestinasiUpdateJadwal.IDJADWAL])

    private fun listNamaDokter() {
        viewModelScope.launch {
            repositoryJadwal.getAllNamaDokter()
                .collect { dokter ->
                    updateJadwalUIState = updateJadwalUIState.copy(listNamaDokter = dokter)
                }
        }
    }

    init {
        listNamaDokter()
        viewModelScope.launch {
            updateJadwalUIState = repositoryJadwal.getJadwal(_idJadwal)
                .filterNotNull()
                .first()
                .toUIStateJadwal()
        }
    }

    fun updateState(jadwalEvent: JadwalEvent){
        updateJadwalUIState = updateJadwalUIState.copy(
            jadwalEvent = jadwalEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateJadwalUIState.jadwalEvent
        val errorState = FormJadwalErrorState(
            dokter = if (event.dokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHpPasien = if (event.noHpPasien.isNotEmpty()) null else "Nomor Hp Pasien tidak boleh kosong",
            tanggal = if (event.tanggal.isNotEmpty()) null else "Tanggal tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong",
        )

        updateJadwalUIState = updateJadwalUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateJadwalUIState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwal.updateJadwal(currentEvent.toJadwalEntity())
                    updateJadwalUIState = updateJadwalUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormJadwalErrorState()
                    )
                } catch (e: Exception) {
                    updateJadwalUIState = updateJadwalUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateJadwalUIState = updateJadwalUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateJadwalUIState = updateJadwalUIState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUIStateJadwal(): JadwalUIState = JadwalUIState(
    jadwalEvent = this.toDetailJadwalUiEvent()
)