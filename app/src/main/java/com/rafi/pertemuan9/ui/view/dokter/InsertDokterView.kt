package com.rafi.pertemuan9.ui.view.dokter

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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import com.rafi.pertemuan9.ui.customwidget.TopAppBar
import com.rafi.pertemuan9.data.objek.PilihSpesialis
import com.rafi.pertemuan9.ui.customwidget.DynamicSelectedTextField
import com.rafi.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterEvent
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterUIState
import com.rafi.pertemuan9.ui.viewmodel.dokter.DokterViewModel
import com.rafi.pertemuan9.ui.viewmodel.dokter.FormErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InsertDokterView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                    contentDescription = "back"
                )
            }
            Spacer(modifier = Modifier.padding(start = 40.dp))
            Text(
                text = "Tambah Dokter",
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
            InsertBodyDokter (
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
fun InsertBodyDokter(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DokterUIState,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDokter(
            dokterEvent = uiState.dokterEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
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

@Composable
fun FormDokter(
    dokterEvent: DokterEvent = DokterEvent(),
    onValueChange: (DokterEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    var chosenDropdown by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.namaDokter,
            onValueChange = {
                onValueChange(dokterEvent.copy(namaDokter = it))
            },
            label = {
                Text("Nama Dokter")
            },
            isError = errorState.namaDokter != null,
            placeholder = {
                Text("Masukkan Nama Dokter")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp)
        )
        Text(
            text = errorState.namaDokter ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        DynamicSelectedTextField(
            selectedValue = chosenDropdown,
            options = PilihSpesialis.options,
            label = "Pilih Spesialis",
            onValueChangedEvent = {
                onValueChange(dokterEvent.copy(spesialis = it))
                chosenDropdown = it
            }
        )
        Text(
            text = errorState.spesialis ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.klinik,
            onValueChange = {
                onValueChange(dokterEvent.copy(klinik = it))
            },
            label = {
                Text("Klinik")
            },
            isError = errorState.klinik != null,
            placeholder = {
                Text("Masukkan Klinik")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp)
        )
        Text(
            text = errorState.klinik ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.nomorHpDokter,
            onValueChange = {
                onValueChange(dokterEvent.copy(nomorHpDokter = it))
            },
            label = {
                Text("Nomor Handphone")
            },
            isError = errorState.nomorHpDokter != null,
            placeholder = {
                Text("Masukkan Nomor Handphone")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(10.dp)
        )
        Text(
            text = errorState.nomorHpDokter ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.jamKerja,
            onValueChange = {
                onValueChange(dokterEvent.copy(jamKerja = it))
            },
            label = {
                Text("Jam Kerja")
            },
            isError = errorState.jamKerja != null,
            placeholder = {
                Text("Masukkan Jam Kerja")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            shape = RoundedCornerShape(10.dp)
        )
        Text(
            text = errorState.jamKerja ?: "",
            color = Color.Red
        )
    }
}