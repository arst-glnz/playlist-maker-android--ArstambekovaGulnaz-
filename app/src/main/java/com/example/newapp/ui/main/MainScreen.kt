package com.example.newapp.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.domain.models.Playlist

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onPlaylistClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color(0xFF3772E7))
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(R.string.pm),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 16.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }

    Box(
        modifier = Modifier
            .padding(top = 84.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            val context = LocalContext.current
            val kar = Icons.AutoMirrored.Filled.KeyboardArrowRight
            MainButton(
                R.drawable.search,
                kar,
                R.string.search,
                onClick = onSearchClick
            )
            MainButton(
                R.drawable.playlist,
                kar,
                R.string.playlists,
                onClick =  onPlaylistClick
            )
            MainButton(
                R.drawable.followed,
                kar, R.string.isbrannoe,
                onClick = onFavoritesClick
            )
            MainButton(
                R.drawable.settings,
                kar,
                R.string.settings,
                onClick = onSettingsClick
            )
        }
    }
}


@Composable
fun MainButton(
    img1: Int,
    img2: ImageVector,
    txt: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp),
        colors = ButtonDefaults.buttonColors(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp),
        elevation = null,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                Image(
                    painterResource(img1),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(24.dp)
                )
                Text(
                    stringResource(txt),
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.Black,
                    fontSize = 22.sp
                )
            }
            Image(
                img2,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(28.dp)
            )
        }
    }
}

