package com.rafi.pertemuan9.ui.view.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.data.entity.Jadwal
import com.rafi.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.rafi.pertemuan9.ui.viewmodel.jadwal.DetailJadwalUiState
import com.rafi.pertemuan9.ui.viewmodel.jadwal.DetailJadwalViewModel
import com.rafi.pertemuan9.ui.viewmodel.jadwal.toJadwalEntity


@Composable
fun DetailJadwalView (
    modifier: Modifier = Modifier,
    viewModel: DetailJadwalViewModel = viewModel (factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
){
   Column(
       modifier = Modifier.fillMaxSize()
           .background(
               brush = Brush.verticalGradient(
                   colors = listOf(
                       Color(0xFFFFC107),
                       Color(0xFFFF5722)
                   )
               )
           )
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
           Spacer(modifier = Modifier.padding(start = 65.dp))
           Text(
               text = "Detail Jadwal",
               fontWeight = FontWeight.Bold,
               fontSize = 30.sp
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
           val detailJadwalUiState by viewModel.detailJadwalUiState.collectAsState()

           BodyDetailJadwal(
               detailJadwalUiState = detailJadwalUiState,
               onDeleteClick = {
                   viewModel.deleteMhs()
                   onDeleteClick()
               }
           )

           FloatingActionButton(
               onClick = {
                   onEditClick(viewModel.detailJadwalUiState.value.detailJadwalUiEvent.idJadwal.toString())
               },
               shape = MaterialTheme.shapes.medium,
               modifier = modifier
                   .align(Alignment.BottomEnd)
                   .padding(16.dp),
               containerColor = Color(0xFFFF5722)
           ) {
               Icon(
                   imageVector = Icons.Default.Edit,
                   contentDescription = "Edit Jadwal",
                   tint = Color.White
               )
           }
       }
   }
}


@Composable
fun BodyDetailJadwal (
    modifier: Modifier = Modifier,
    detailJadwalUiState: DetailJadwalUiState = DetailJadwalUiState(),
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailJadwalUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Tampilkan loading
            }
        }

        detailJadwalUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailJadwal(
                    jadwal = detailJadwalUiState.detailJadwalUiEvent.toJadwalEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button (
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        containerColor = Color(0xFFFF5722),
                        disabledContainerColor = Color(0xFFFF5722)
                    )
                ) {
                    Text(text = "Delete", fontWeight = FontWeight.Bold)
                }
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailJadwalUiState.isUiEventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailJadwal (
    modifier: Modifier = Modifier,
    jadwal: Jadwal
){
    Card (
        modifier = modifier
            .fillMaxWidth (),
        colors = CardDefaults.cardColors (
            containerColor = Color(0xFFFF5722),
            contentColor = Color.White
        )
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            ComponentDetailJadwal (judul = "ID Jadwal", isinya = jadwal.idJadwal.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJadwal (judul = "Nama Pasien", isinya = jadwal.namaPasien)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJadwal (judul = "Nomor Handphone Pasien", isinya = jadwal.noHpPasien)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJadwal (judul = "Nama Dokter", isinya = jadwal.dokter)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJadwal (judul = "Tanggal", isinya = jadwal.tanggal)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJadwal (judul = "Status Penanganan", isinya = jadwal.status)
        }
    }
}

@Composable
fun ComponentDetailJadwal (
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontStyle = FontStyle.Italic

        )
        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun DeleteConfirmationDialog (
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier =
        Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton (onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}