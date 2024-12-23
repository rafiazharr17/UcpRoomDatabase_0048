package com.rafi.pertemuan9.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.data.entity.Dokter
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

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFFFF5722))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color.White)
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.padding(start = 40.dp))
            Text(
                text = "Tambah Jadwal",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif
            )
        }

        Box (
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp)
                )
        ) {
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
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                contentColor = Color.White,
                disabledContentColor = Color.White,
                containerColor = Color(0xFFFF5722),
                disabledContainerColor = Color(0xFFFF5722)
            )
        ) {
            Text(
                text = "Simpan",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit,
    errorState: FormJadwalErrorState = FormJadwalErrorState(),
    modifier: Modifier = Modifier
){
    var chosenDropdown by remember { mutableStateOf("") }

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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp)
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(10.dp)
        )
        Text(
            text = errorState.noHpPasien ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        DynamicSelectedTextField(
            selectedValue = jadwalEvent.dokter,
            options = listNamaDokter.map { it.namaDokter }.also {
                println("Dropdown options: $it")
            },
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp)
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp)
        )
        Text(
            text = errorState.status ?: "",
            color = Color.Red
        )
    }
}