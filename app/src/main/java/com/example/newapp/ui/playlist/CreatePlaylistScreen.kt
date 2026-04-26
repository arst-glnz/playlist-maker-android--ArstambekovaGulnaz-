package com.example.newapp.ui.playlist

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaylistScreen(
    onCreateClick: (String, String) -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок с кнопкой назад
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black
                )
            }
            Text(
                text = "Новый плейлист",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Поле ввода названия
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название*") },
            placeholder = { Text("Введите название плейлиста") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3772E7),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF3772E7),
                cursorColor = Color(0xFF3772E7)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Поле ввода описания
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            placeholder = { Text("Добавьте описание плейлиста") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3772E7),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF3772E7),
                cursorColor = Color(0xFF3772E7)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка Создать
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    onCreateClick(name, description)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = name.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3772E7)
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Создать",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}