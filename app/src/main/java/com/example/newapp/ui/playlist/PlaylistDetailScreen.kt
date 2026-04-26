package com.example.newapp.ui.playlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.data.network.Track
import com.example.newapp.domain.models.Playlist
import com.example.newapp.presentation.PlaylistsViewModel
import com.example.newapp.ui.search.TrackListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreen(
    playlistId: Long,
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit,
    onTrackClick: (Long) -> Unit  // ← ДОБАВИЛ для навигации к треку
) {
    val scope = rememberCoroutineScope()
    var playlist by remember { mutableStateOf<Playlist?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    LaunchedEffect(playlistId) {
        playlistsViewModel.playlists.collect { playlists ->
            playlist = playlists.find { it.id == playlistId }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок
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
                text = playlist?.name ?: "Плейлист",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            IconButton(onClick = { /* Меню действий */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Меню",
                    tint = Color.Black
                )
            }
        }

        // Информация о плейлисте
        if (playlist != null) {
            Text(
                text = "${playlist!!.tracks.size} треков",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 48.dp, bottom = 8.dp)
            )

            if (playlist!!.description.isNotEmpty()) {
                Text(
                    text = playlist!!.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 48.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
        }

        HorizontalDivider()

        // Список треков
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(playlist?.tracks ?: emptyList()) { track ->
                TrackListItem(
                    track = track,
                    onClick = {
                        onTrackClick(track.id)  // ← Переход к деталям трека
                    },
                    onLongClick = {
                        trackToDelete = track
                        showDeleteDialog = true  // ← Показать диалог удаления
                    }
                )
            }
        }
    }

    // Диалог подтверждения удаления трека
    if (showDeleteDialog && trackToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                trackToDelete = null
            },
            title = { Text("Удалить трек") },
            text = { Text("Вы уверены, что хотите удалить трек \"${trackToDelete?.trackName}\" из плейлиста?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        trackToDelete?.let { track ->
                            scope.launch {
                                playlistsViewModel.deleteTrackFromPlaylist(track)
                            }
                        }
                        showDeleteDialog = false
                        trackToDelete = null
                    }
                ) {
                    Text("Удалить", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        trackToDelete = null
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}