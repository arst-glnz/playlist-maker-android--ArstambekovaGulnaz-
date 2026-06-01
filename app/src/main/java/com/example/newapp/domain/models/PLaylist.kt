package com.example.newapp.domain.models

import com.example.newapp.data.network.Track

data class Playlist(
    val id: Long,
    val name: String,
    val description: String,
    var tracks: List<Track>,
    val tracksCount: Int = 0,
    val coverUrl: String = ""
)