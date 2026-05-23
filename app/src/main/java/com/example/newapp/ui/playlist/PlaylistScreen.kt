package com.example.newapp.ui.playlist

import android.R.attr.font
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import com.example.newapp.R
import com.example.newapp.domain.models.Playlist
import com.example.newapp.presentation.PlaylistsViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (playlist.coverUrl.isNotBlank()) {
            AsyncImage(
                model = playlist.coverUrl,
                contentDescription = playlist.name,
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.add_icon)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.add_icon),
                contentDescription = playlist.name,
                modifier = Modifier.size(45.dp),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                playlist.name,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.regular)),
                maxLines = 1
            )

            Text(
                text = "${playlist.tracksCount} треков",
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
    var showDeleteDialog by remember { mutableStateOf(false) }
    var playlistToDelete by remember { mutableStateOf<Playlist?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    modifier = Modifier.padding(start = 50.dp, top = 14.dp)
                )
            }

            // cписок плейлистов
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(playlists.size) { index ->
                    PlaylistListItem(
                        playlist = playlists[index],
                        onClick = { navigateToPlaylist(playlists[index].id) },
                        onLongClick = {  //
                            playlistToDelete = playlists[index]
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = addNewPlaylist,
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.BottomEnd),

            containerColor = Color.Transparent,
            contentColor = Color.Unspecified,

            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp
            ),

            shape = CircleShape
        ) {
            Image(
                modifier = Modifier.size(51.dp),
                painter = painterResource(R.drawable.add_in_playlist),
                contentDescription = "Создать плейлист"
            )
        }
    }
    if (showDeleteDialog && playlistToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                playlistToDelete = null
            },
            title = {
                Text(
                    text = "Удалить плейлист?",
                    fontFamily = FontFamily(Font(R.font.medium)),
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = "Вы уверены, что хотите удалить плейлист «${playlistToDelete?.name}»?",
                    fontFamily = FontFamily(Font(R.font.regular)),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        playlistToDelete?.let { playlist ->
                            playlistsViewModel.deletePlaylist(playlist.id)
                        }
                        showDeleteDialog = false
                        playlistToDelete = null
                    }
                ) {
                    Text(
                        text = "Удалить",
                        color = Color.Red,
                        fontFamily = FontFamily(Font(R.font.medium))
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        playlistToDelete = null
                    }
                ) {
                    Text(
                        text = "Отмена",
                        fontFamily = FontFamily(Font(R.font.medium))
                    )
                }
            }
        )
    }
}
