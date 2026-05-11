package com.example.newapp.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
            .padding(top = 16.dp)
    ) {
        // Заголовок с кнопкой назад
        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Новый плейлист",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                fontWeight = FontWeight.Medium,
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .height(412.dp)
        ) {

            // Обложка
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center

            ) {
                Image(
                    painter = painterResource(id = R.drawable.add_photo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
        }

        // Поле ввода названия
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = stringResource(R.string.playlists_name),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.regular))
                )
            },
            placeholder = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.yandex_purple),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = colorResource(R.color.yandex_purple),
                cursorColor = colorResource(R.color.yandex_purple)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Поле ввода описания
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = {
                Text(
                    text = stringResource(R.string.description),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.regular))
                )
            },
            placeholder = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            maxLines = 3,
            minLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3772E7),
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color(0xFF3772E7),
                cursorColor = Color(0xFF3772E7)
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка Создать
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    onCreateClick(name, description)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp, bottom = 32.dp)
                .height(44.dp)
                .align(Alignment.End),

            enabled = name.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.yandex_purple),
                disabledContainerColor = colorResource(R.color.grey)
            ),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = "Создать",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                color = Color.White
            )
        }
    }
}