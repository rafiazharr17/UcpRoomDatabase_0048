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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.data.entity.Jadwal
import com.rafi.pertemuan9.ui.customwidget.TopAppBar
import com.rafi.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.rafi.pertemuan9.ui.viewmodel.jadwal.HomeJadwalUiState
import com.rafi.pertemuan9.ui.viewmodel.jadwal.HomeJadwalViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeJadwalView(
    viewModel: HomeJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddJadwal: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    navigateLihatDokter: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFFF5722))
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Rumah Sehat",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Rafi Alif Azhar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White,
                    fontFamily = FontFamily.Cursive
                )
            }


            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }

        Card (
            modifier = Modifier.fillMaxSize(),
            shape = RectangleShape
        ) {
            TopAppBar(
                judul = "Daftar Jadwal",
                modifier = Modifier.padding(16.dp),
                navigateDokter = navigateLihatDokter,
                navigateJadwal = onAddJadwal,
                showDokterButton = true,
                showJadwalButton = true,
                showSearch = true,
                judulSearch = "Cari Jadwal",
                judulButtonJadwal = "Tambah Jadwal",
                judulButtonDokter = "Lihat Dokter"
            )

            val homeJadwalUiState by viewModel.homeJadwalUiState.collectAsState()

            BodyHomeJadwalView(
                homeJadwalUiState = homeJadwalUiState,
                onClick = {
                    onDetailClick(it)
                }
            )
        }
    }
}

@Composable
fun BodyHomeJadwalView(
    homeJadwalUiState: HomeJadwalUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    when {
        homeJadwalUiState.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeJadwalUiState.isError -> {
            LaunchedEffect(homeJadwalUiState.errorMessage) {
                homeJadwalUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) //Tampilkan Snackbar
                    }
                }
            }
        }

        homeJadwalUiState.listJadwal.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada jadwal.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListJadwal(
                listJadwal = homeJadwalUiState.listJadwal,
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
fun ListJadwal(
    listJadwal: List<Jadwal>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
){
    LazyColumn(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(
            items = listJadwal,
            itemContent = {jadwal ->
                CardJadwal(
                    jadwal = jadwal,
                    onClick = {
                        onClick(jadwal.idJadwal.toString())
                    }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardJadwal(
    jadwal: Jadwal,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
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
                modifier = Modifier.size(100.dp),
                tint = Color(0xFFFF5722)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = jadwal.namaPasien,
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
                        text = jadwal.status,
                        fontWeight = FontWeight.Bold,
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
                        text = jadwal.tanggal,
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
                        imageVector = Icons.Sharp.Phone,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = jadwal.noHpPasien,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

    }
}