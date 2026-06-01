package com.example.newapp.ui.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.data.network.Track
import com.example.newapp.presentation.PlaylistsViewModel
import com.example.newapp.ui.search.TrackListItem

@Composable
fun FavoritesScreen(
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit,
    onTrackClick: (Track) -> Unit
) {
    val favoriteTracks by playlistsViewModel.favoriteList.collectAsState(initial = emptyList())
    var trackToDelete by remember { mutableStateOf<Track?>(null) }
    trackToDelete?.let { track ->

        AlertDialog(
            onDismissRequest = {
                trackToDelete = null
            },
            title = {
                Text("Удалить из избранного?")
            },
            text = {
                Text(track.trackName)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        playlistsViewModel.removeFromFavorites(track)
                        trackToDelete = null
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        trackToDelete = null
                    }
                ) {
                    Text("Отмена")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = Color.Black)
            }
            Text(
                text = "Избранное",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.medium)),
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (favoriteTracks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет избранных треков", color = Color.Gray, fontSize = 18.sp)
            }
        } else {
            LazyColumn {
                items(favoriteTracks) { track ->
                    TrackListItem(
                        track = track,
                        onClick = { onTrackClick(track) },
                        onLongClick = {
                            trackToDelete = track
                        }
                    )
                }
            }
        }
    }
}