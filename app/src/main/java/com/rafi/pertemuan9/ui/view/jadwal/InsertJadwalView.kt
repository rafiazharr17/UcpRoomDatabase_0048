package com.rafi.pertemuan9.ui.view.jadwal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.ui.customwidget.DynamicSelectedTextField
import com.rafi.pertemuan9.ui.customwidget.TopAppBar
import com.rafi.pertemuan9.ui.view.dokter.FormDokter
import com.rafi.pertemuan9.ui.view.dokter.InsertBodyDokter
import com.rafi.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterEvent
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterUIState
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterViewModel
import com.rafi.pertemuan9.ui.viewmodel.jadwal.FormJadwalErrorState
import com.rafi.pertemuan9.ui.viewmodel.jadwal.JadwalEvent
import com.rafi.pertemuan9.ui.viewmodel.jadwal.JadwalUIState
import com.rafi.pertemuan9.ui.viewmodel.jadwal.JadwalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InsertJadwalView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Jadwal",
                modifier = modifier,
                navigateDokter = {},
                navigateJadwal = {},
                showDokterButton = false,
                showJadwalButton = false,
                showSearch = false,
                judulSearch = "",
                judulButtonJadwal = "",
                judulButtonDokter = ""
            )

            InsertBodyJadwal (
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.UpdateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()){
                            viewModel.saveData()
                            delay(500)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun InsertBodyJadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JadwalUIState,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = Color(0xFF00AAEC),
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color(0xFF00AAEC)
            )
        ) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    viewModel: JadwalViewModel = viewModel(),
    onValueChange: (JadwalEvent) -> Unit,
    errorState: FormJadwalErrorState = FormJadwalErrorState(),
    modifier: Modifier = Modifier
){
    var chosenDropdown by remember { mutableStateOf("") }

    var opsiPilihDokter by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaPasien = it))
            },
            label = {
                Text("Nama Pasien")
            },
            isError = errorState.namaPasien != null,
            placeholder = {
                Text("Masukkan Nama Pasien")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.namaPasien ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.noHpPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(noHpPasien = it))
            },
            label = {
                Text("Nomor Handphone Pasien")
            },
            isError = errorState.noHpPasien != null,
            placeholder = {
                Text("Masukkan Nomor Handphone")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.noHpPasien ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mengambil Data Nama Dokter dari ViewModel
        LaunchedEffect (Unit) {
            viewModel.listDokter.collect { dokterList ->
                opsiPilihDokter = dokterList.map { it.namaDokter }
            }
        }

        DynamicSelectedTextField(
            selectedValue = chosenDropdown,
            options = opsiPilihDokter,
            label = "Pilih Nama Dokter",
            onValueChangedEvent = {
                onValueChange(jadwalEvent.copy(dokter = it))
                chosenDropdown = it
            }
        )
        Text(
            text = errorState.dokter ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.tanggal,
            onValueChange = {
                onValueChange(jadwalEvent.copy(tanggal = it))
            },
            label = {
                Text("Tanggal")
            },
            isError = errorState.tanggal != null,
            placeholder = {
                Text("Masukkan Tanggal")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.tanggal ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.status,
            onValueChange = {
                onValueChange(jadwalEvent.copy(status = it))
            },
            label = {
                Text("Status Penanganan")
            },
            isError = errorState.status != null,
            placeholder = {
                Text("Masukkan Status Penanganan")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.status ?: "",
            color = Color.Red
        )
    }
}