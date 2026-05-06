package com.example.newapp.ui.playlist

import android.R.attr.font
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.domain.models.Playlist
import com.example.newapp.presentation.PlaylistsViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PlaylistListItem(playlist: Playlist, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = playlist.name,
            modifier = Modifier.size(45.dp),
            colorFilter = ColorFilter.tint(Color.Gray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.Start
        ) {
            Text(
                playlist.name,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                maxLines = 1
            )

            Text(
                text = "${playlist.tracks.size} треков",
                fontSize = 11.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PlaylistsScreen(
    modifier: Modifier,
    playlistsViewModel: PlaylistsViewModel,
    addNewPlaylist: () -> Unit,
    navigateToPlaylist: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    val playlists by playlistsViewModel.playlists.collectAsState(emptyList())

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            // Заголовок с кнопкой назад
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .padding(top = 14.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.playlists),
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.medium)),
                    modifier = Modifier.padding(start = 48.dp, top = 14.dp)
                )
            }

            // Список плейлистов
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
            ) {
                items(playlists.size) { index ->
                    PlaylistListItem(playlist = playlists[index]) {
                        navigateToPlaylist(playlists[index].id)
                    }
                }
            }
        }

        // FloatingActionButton поверх всего
        FloatingActionButton(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd),
            onClick = addNewPlaylist,
            containerColor = colorResource(R.color.add_gray),
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                modifier = Modifier.size(23.5.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = "Создать плейлист"
            )
        }
    }
}
