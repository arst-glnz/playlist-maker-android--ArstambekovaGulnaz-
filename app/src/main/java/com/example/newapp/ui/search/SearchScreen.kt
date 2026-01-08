package com.example.newapp.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.presentation.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onBackClick: () -> Unit = {}
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Box(
            modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(top = 14.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = stringResource(R.string.search),
                fontSize = 22.sp,
                fontWeight = Medium,
                modifier = Modifier.padding(start = 48.dp, top = 14.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    color = Color(0xFF818C99),
                    fontSize = 20.sp
                )
            },
            leadingIcon = {
                IconButton(
                    onClick = {viewModel.search(query)},
                    //modifier = Modifier.padding(start = 8.dp)
                    //передали значение стейта guery в кач-ве строки поиска
                ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF818C99),
                    modifier = Modifier.padding(start = 3.dp),
                )
                }
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            },
            singleLine = false,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE6E8EB),
                unfocusedContainerColor = Color(0xFFE6E8EB),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        //обработка поиска
        when (screenState) {
            is SearchState.Initial -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Введите строку для поиска")
                }
            }

            is SearchState.Searching -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list
                LazyColumn(
                    modifier = modifier.fillMaxSize()
                ) {
                    items(tracks.size) { index ->
                        TrackListItem(track = tracks[index])
                        HorizontalDivider(thickness = 0.5.dp)
                    }
                }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Ошибка: $error", color = Color.Red)
                }
            }
        }
    }
}

