package com.example.newapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
            //.padding(horizontal = 16.dp)
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick?.invoke() }
            )
    ) {
// Обложка трека
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            if (track.image.isNotEmpty()) {
                AsyncImage(
                    model = track.image,                    // artworkUrl100
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.music_note), // заглушка
                    error = painterResource(id = R.drawable.music_note)        // если ошибка
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.music_note),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

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
                fontFamily = FontFamily(Font(R.font.regular)),
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
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.regular)),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    letterSpacing = (-0.1).sp
                )

                Text(
                    text = " • ${track.trackTime}",
                    color = colorResource(R.color.grey),
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(R.font.regular))
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

/*@Preview(showBackground = true, showSystemUi = true)
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
}*/

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun History(
   // historyList: List<String>,
    //onClick: (String) -> Unit
) {
    val historyList = listOf("Tom", "Sam", "Kate", "Bob", "Alice")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 250.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E8EB))
    ) {
        LazyColumn {
            itemsIndexed(historyList) { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.clickable { onClick(item) }
                        .padding(horizontal = 16.dp, vertical = 13.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = Color(0xFF818C99),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = item,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}