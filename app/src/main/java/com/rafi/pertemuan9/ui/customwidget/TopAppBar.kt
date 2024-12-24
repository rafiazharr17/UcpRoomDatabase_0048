package com.rafi.pertemuan9.ui.customwidget

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navigate: () -> Unit,
    judul: String,
    judulSearch: String,
    judulButton: String,
    modifier: Modifier
) {
    var search by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFFC107),
                        Color(0xFFFF5722)
                    )
                ),
                shape = RoundedCornerShape(bottomStart = 70.dp, topEnd = 70.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = judul,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.White,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            placeholder = { Text(text = judulSearch) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = navigate,
            colors = ButtonColors(
                contentColor = Color(0xFFFF5722),
                disabledContentColor = Color(0xFFFF5722),
                containerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            Text(
                text = judulButton,
                fontWeight = FontWeight.Bold
            )
        }
    }
}