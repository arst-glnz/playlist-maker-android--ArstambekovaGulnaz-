package com.example.newapp.ui.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.data.network.Track
import com.example.newapp.domain.models.Playlist
import com.example.newapp.presentation.PlaylistsViewModel
import com.example.newapp.presentation.SearchViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailScreen(
    trackId: Long,
    playlistsViewModel: PlaylistsViewModel,
    searchViewModel: SearchViewModel,
    onBackClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var track by remember { mutableStateOf<Track?>(null) }
    var playlists by remember { mutableStateOf<List<Playlist>>(emptyList()) }

    LaunchedEffect(Unit) {
        playlistsViewModel.playlists.collect { playlistList ->
            playlists = playlistList
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
    ) {
        // Верхняя панель с кнопкой назад
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.White
                )
            }
        }

        // Обложка и информация о треке
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Обложка
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF2D2D2D)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.music_note),
                    contentDescription = "Обложка трека",
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Название трека
            Text(
                text = track?.trackName ?: "Название трека",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Исполнитель
            Text(
                text = track?.artistName ?: "Исполнитель",
                color = Color(0xFFB0B0B0),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопки действий
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Кнопка "Избранное"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        track?.let {
                            scope.launch {
                                playlistsViewModel.toggleFavorite(it, !it.favorite)
                                track = track?.copy(favorite = !it.favorite)
                            }
                        }
                    }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (track?.favorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (track?.favorite == true) Color.Red else Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (track?.favorite == true) "В избранном" else "В избранное",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            // Кнопка "Добавить в плейлист"
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { showBottomSheet = true }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlaylistAdd,
                    contentDescription = "В плейлист",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "В плейлист",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }

    // Bottom Sheet с плейлистами
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Добавить в плейлист",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (playlists.isEmpty()) {
                    Text(
                        text = "Нет доступных плейлистов",
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 24.dp)
                    )
                } else {
                    LazyColumn {
                        items(playlists) { playlist ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        track?.let {
                                            scope.launch {
                                                playlistsViewModel.insertTrackToPlaylist(
                                                    it,
                                                    playlist.id
                                                )
                                                showBottomSheet = false
                                            }
                                        }
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.music_note),
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = playlist.name,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "${playlist.tracks.size} треков",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}