package com.example.newapp.ui.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
        playlistsViewModel.playlists.collect {
            playlists = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // Back
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

        // Контент
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
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFE53935))
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.music_note),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = track?.trackName ?: "Yesterday (Remastered 2009)",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = track?.artistName ?: "The Beatles",
                fontSize = 16.sp,
                color = Color(0xFF8A8A8A)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // КНОПКИ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Playlist
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
                    .clickable { showBottomSheet = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Favorite
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
                    .clickable {
                        track?.let {
                            scope.launch {
                                playlistsViewModel.toggleFavorite(it, !it.favorite)
                                track = it.copy(favorite = !it.favorite)
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (track?.favorite == true)
                        Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // ⏱ ДЛИТЕЛЬНОСТЬ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Длительность",
                fontSize = 14.sp,
                color = Color(0xFF9E9E9E)
            )

            Text(
                text = "5:35",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }

    // BottomSheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                // handle
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .size(width = 34.dp, height = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(Color(0xFFD0D0D0))
                    )
                }

                Text(
                    text = "Добавить в плейлист",
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (playlists.isEmpty()) {
                    Text(
                        text = "Нет доступных плейлистов",
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.regular)),
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
                                        fontFamily = FontFamily(Font(R.font.regular)),
                                        color = Color.Black
                                    )

                                    Text(
                                        text = "${playlist.tracks.size} треков",
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily(Font(R.font.regular)),
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