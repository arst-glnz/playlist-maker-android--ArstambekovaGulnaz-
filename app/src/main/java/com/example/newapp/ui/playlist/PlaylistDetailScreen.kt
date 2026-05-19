package com.example.newapp.ui.playlist

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

    val fakePlaylist = Playlist(
        id = 1,
        name = "Best songs 2021",
        description = "2022",
        tracks = listOf(

            Track(
                id = 1,
                trackName = "Yesterday (Remastered 2009)",
                artistName = "The Beatles",
                trackTime = "2:55",
                image = "",
                favorite = false,
            ),

            Track(
                id = 2,
                trackName = "Here Comes The Sun (Remastered 2009)",
                artistName = "The Beatles",
                trackTime = "2:55",
                image = "",
                favorite = false,
            ),

            Track(
                id = 3,
                trackName = "No Reply",
                artistName = "The Beatles",
                trackTime = "5:41",
                image = "",
                favorite = false,
            ),

            Track(
                id = 4,
                trackName = "Let It Be",
                artistName = "The Beatles",
                trackTime = "3:11",
                image = "",
                favorite = false,
            )
        )
    )

    var playlist by remember {
        mutableStateOf(fakePlaylist)
    }

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

        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {

            Text(
                text = playlist?.name ?: "Best songs 2021",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.bold)),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "2022",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "300 минут • ${playlist?.tracks?.size ?: 98} треков",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

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

            items(playlist?.tracks ?: emptyList()) { track ->
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

