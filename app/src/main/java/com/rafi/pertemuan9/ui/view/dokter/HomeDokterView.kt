package com.rafi.pertemuan9.ui.view.dokter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.ui.customwidget.TopAppBar
import com.rafi.pertemuan9.data.entity.Dokter
import com.rafi.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.rafi.pertemuan9.ui.viewmodel.dokter.HomeDokterUiState
import com.rafi.pertemuan9.ui.viewmodel.dokter.HomeDokterViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeDokterView(
    viewModel: HomeDokterViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDokter: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    navigateLihatJadwal: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 18.dp),
        topBar = {
            TopAppBar(
                judul = "Daftar Dokter",
                showBackButton = false,
                onBack = { },
                modifier = modifier,
                navigateDokter = onAddDokter,
                navigateJadwal = navigateLihatJadwal,
                showDokterButton = true,
                showJadwalButton = true,
                showSearch = true,
                judulSearch = "Cari Dokter",
                judulButtonDokter = "Tambah Dokter",
                judulButtonJadwal = "Lihat Jadwal"
            )
        },
    ) { innerPadding ->
        val homeDokterUiState by viewModel.homeDokterUiState.collectAsState()

        BodyHomeDokterView(
            homeDokterUiState = homeDokterUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeDokterView(
    homeDokterUiState: HomeDokterUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    when {
        homeDokterUiState.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeDokterUiState.isError -> {
            LaunchedEffect(homeDokterUiState.errorMessage) {
                homeDokterUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) //Tampilkan Snackbar
                    }
                }
            }
        }

        homeDokterUiState.listDokter.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dokter.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListDokter(
                listDokter = homeDokterUiState.listDokter,
                onClick = {
                    onClick(it)
                    println(
                        it
                    )
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListDokter(
    listDokter: List<Dokter>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
){
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listDokter,
            itemContent = {dokter ->
                CardDokter(
                    dokter = dokter,
                    onClick = {
                        onClick(dokter.idDokter.toString())
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDokter(
    dokter: Dokter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    fun spesialisColor(spesialis: String): Color {
        return when (spesialis) {
            "Spesialis Anak" -> Color.Blue
            "Spesialis Bedah Saraf" -> Color.Gray
            "Spesialis Akupuntur Medik" -> Color.Yellow
            "Spesialis Bedah" -> Color.Green
            else -> Color.Black
        }
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dokter.namaDokter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dokter.spesialis,
                        fontWeight = FontWeight.Bold,
                        color = spesialisColor(dokter.spesialis)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Sharp.Home,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = dokter.klinik,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Sharp.DateRange,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = dokter.jamKerja,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

    }
}