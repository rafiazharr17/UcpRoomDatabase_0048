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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.R
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
    Column(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF5722),
                        Color(0xFFFFC107)
                    )
                )
            )
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

        Card {
            TopAppBar(
                judul = "Daftar Dokter",
                modifier = Modifier.padding(16.dp),
                navigate = navigateLihatJadwal,
                judulSearch = "Cari Dokter",
                judulButton = "Lihat Jadwal"
            )

            Box (
                modifier = Modifier.fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                val homeDokterUiState by viewModel.homeDokterUiState.collectAsState()

                BodyHomeDokterView(
                    homeDokterUiState = homeDokterUiState,
                    onClick = {
                        onDetailClick(it)
                    },
                )

                FloatingActionButton(
                    onClick = onAddDokter,
                    shape = MaterialTheme.shapes.medium,
                    modifier = modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = Color(0xFFFF5722)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Dokter",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
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
                CircularProgressIndicator(color = Color(0xFF00AAEC))
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
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            ListDokter(
                listDokter = homeDokterUiState.listDokter,
                onClick = {
                    onClick(it)
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
) {
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
            items = listDokter,
            itemContent = { dokter ->
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
            "Spesialis Anak" -> Color(0xFF42A5F5)
            "Spesialis Bedah Saraf" -> Color(0xFFAB47BC)
            "Spesialis Akupuntur Medik" -> Color(0xFF26A69A)
            "Spesialis Bedah" -> Color(0xFF66BB6A)
            else -> Color(0xFF757575)
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
                        text = dokter.namaDokter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = FontFamily.Serif
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dokter.spesialis,
                        fontWeight = FontWeight.Bold,
                        color = spesialisColor(dokter.spesialis),
                        fontStyle = FontStyle.Italic
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_hospital),
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
                        painter = painterResource(id = R.drawable.waktu),
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
