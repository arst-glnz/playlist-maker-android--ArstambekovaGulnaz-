package com.example.newapp.data.dto

class TrackDto (
    val trackId: Long,           // ← было id, теперь trackId
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val previewUrl: String?,
    val artworkUrl100: String?
)

