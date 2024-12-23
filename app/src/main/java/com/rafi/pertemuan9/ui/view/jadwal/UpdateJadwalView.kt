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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.rafi.pertemuan9.ui.viewmodel.jadwal.UpdateJadwalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateJadwalView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiJadwalState = viewModel.updateJadwalUIState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect (uiJadwalState.snackBarMessage) {
        println("LaunchedEffect triggered")
        uiJadwalState.snackBarMessage?.let { message ->
            println("Launching coroutine: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
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
            Spacer(modifier = Modifier.padding(start = 60.dp))
            Text(
                text = "Update Jadwal",
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
            InsertBodyJadwal (
                uiState = uiJadwalState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()){
                            viewModel.updateData()
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