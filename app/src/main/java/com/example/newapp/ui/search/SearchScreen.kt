package com.example.newapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.presentation.SearchViewModel
import com.example.newapp.ui.search.HistoryRequests  // ← твой компонент из курса
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel,
    onClick: (Int?) -> Unit
) {
    val screenState by searchViewModel.searchScreenState.collectAsState()
    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(text) {
        searchViewModel.updateQuery(text)
    }

    LaunchedEffect(screenState) {
        when (screenState) {
            is SearchState.Success -> {
                focusManager.clearFocus()
            }

            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        searchViewModel.historyList.collect { list ->
            historyList = list
        }
    }

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
                { onClick(null) },
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
            value = text,
            onValueChange = { newText ->
                text = newText
            },

            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    color = Color(0xFF818C99),
                    fontSize = 20.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF818C99),
                    modifier = Modifier.padding(start = 3.dp),
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            text = ""
                            searchViewModel.clearSearch()
                        }
                    ) {
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
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },

            )

        Spacer(modifier = Modifier.height(8.dp))

        // === Отображение контента ===
        when (screenState) {
            is SearchState.Initial -> {
                if (text.isEmpty() && historyList.isNotEmpty()) {
                    Spacer(modifier = Modifier.height((-8).dp))           // небольшой отступ от поля поиска
                    HistoryRequests(
                        historyList = historyList,
                        onClick = { word ->
                            text = word
                        }
                    )
                } else if (text.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.search_placeholder))
                    }
                }
            }
            // ... остальные состояния
            is SearchState.Searching -> {  // Только один
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).list

                if (tracks.isEmpty()) {
                    //Ничего не нашлось
                    NothingFoundScreen()
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(tracks) { track ->
                            TrackListItem(
                                track = track,
                                onClick = { onClick(track.id.toInt()) }
                            )
                        }
                    }
                }
            }


            is SearchState.Fail -> {
                Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.error), color = Color.Red)
                        Text(
                            (screenState as SearchState.Fail).error,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NothingFoundScreen() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 112.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.is_nothing_found), // ← твоя картинка
                contentDescription = "Ничего не нашлось",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.nothing_found),
                fontSize = 19.sp,
                fontWeight = Medium,
                color = Color.Black
            )
        }
    }
}