package com.example.newapp.ui.playlist

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.data.db.entity.TrackEntity
import com.example.newapp.data.network.Track
import com.example.newapp.domain.models.Playlist
import com.example.newapp.presentation.PlaylistsViewModel
import com.example.newapp.ui.search.TrackListItem

@Composable
fun PlaylistDetailScreen(
    playlistId: Long,
    playlistsViewModel: PlaylistsViewModel,
    onBackClick: () -> Unit,
    onTrackClick: (Track) -> Unit
) {
    val scope = rememberCoroutineScope()

    val playlistWithTracks by playlistsViewModel
        .playlistsRepository
        .getPlaylistWithTracks(playlistId)
        .collectAsState(initial = null)

    val playlist = playlistWithTracks?.playlist
    val tracks = playlistWithTracks?.tracks ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
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

        }

        IconButton(onClick = {
            scope.launch {
                playlistsViewModel.deletePlaylistById(playlistId)
                onBackClick()
            }
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить плейлист",
                tint = Color.Red
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .height(300.dp)
        ) {

            // Обложка
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(100.dp)
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

        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {

            Text(
                text = playlist?.name ?: "Плейлист",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.bold)),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = playlist?.description ?: "",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "${tracks.sumOf { it.trackTime.toMinutesSafe() }} мин • ${tracks.size} треков",
                fontSize = 16.sp,
                color = Color.Black
            )
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color(0xFF1C1B1F)
                )
            }
        }

        // TRACKS
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 13.dp, end = 19.dp)
        ) {

            items(tracks) { entity ->

                val track = entity.toTrack()

                TrackListItem(
                    track = track,
                    onClick = {
                        onTrackClick(track)
                    }
                )
            }
        }

    }
}

private fun TrackEntity.toTrack(): Track {

    return Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        image = image,
        previewUrl = previewUrl,
        favorite = favorite
    )
}

private fun String.toMinutesSafe(): Int {

    return try {

        val parts = split(":")

        val minutes = parts[0].toInt()

        val seconds = parts[1].toInt()

        if (seconds > 0) {
            minutes + 1
        } else {
            minutes
        }

    } catch (e: Exception) {
        0
    }
}
