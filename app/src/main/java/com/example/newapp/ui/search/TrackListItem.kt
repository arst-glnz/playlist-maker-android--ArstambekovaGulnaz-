package com.example.newapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newapp.R
import com.example.newapp.data.network.Track

@Composable
fun TrackListItem(
    track: Track,
    onLongClick: (() -> Unit)? = null, //при долгом нажатии на элемент;
    onClick: () -> Unit = {} //при обычном нажатии на элемент.
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick?.invoke() }
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.music_note),
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = track.trackName,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = (-0.1).sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = track.artistName,
                    color = colorResource(R.color.grey),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = (-0.1).sp
                )

                Text(
                    text = " • ${track.trackTime}",
                    color = colorResource(R.color.grey),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

        }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colorResource(R.color.grey),
                modifier = Modifier
                    .size(34.dp)
            )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrackListItemPreview() {
    val sampleTracks = listOf(
        Track(
            trackName = "Yesterday (Remastered 2009)",
            artistName = "The Beatles",
            trackTime = "2:55",
        ),
        Track(
            trackName = "Here Comes The Sun (Remastered...)",
            artistName = "The Beatles",
            trackTime = "4:01",
        ),
        Track(
            trackName = "No Reply",
            artistName = "The Beatles",
            trackTime = "5:12",
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        sampleTracks.forEach { track ->
            TrackListItem(track = track)
        }
    }
}