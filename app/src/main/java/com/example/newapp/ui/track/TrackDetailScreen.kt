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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            .padding(top =16.dp)
            .background(Color.White)
            .fillMaxSize()
    ) {

        // Back
        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

        // Контент
        Column(
            modifier = Modifier
                .padding(top = 26.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .height(412.dp)
        ) {

            // Обложка
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = colorResource(R.color.ll_grey)),
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = track?.trackName ?: "Название трека",
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(start = 24.dp),
            text = track?.artistName ?: "Артист",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.medium)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(54.dp))

        // КНОПКИ
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Playlist
            Box(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .size(51.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFBDBDBD))
                    .clickable { showBottomSheet = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddToPhotos,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Favorite
            Box(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .size(51.dp)
                    .clip(CircleShape)
                    .background(color = colorResource(R.color.add_gray))
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

        Spacer(modifier = Modifier.height(33.dp))

        // длительность
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.track_time),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = colorResource(R.color.grey)
            )

            Text(
                text = "5:35",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = Color.Black
            )
        }


        // BottomSheet
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                //sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

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
                        text = stringResource(R.string.add_playlist),
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
                            }
                        }
                    }

                }
            }
        }
    }
}