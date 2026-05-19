package com.example.newapp.data.network

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String,
    val previewUrl: String? = null,
    val favorite: Boolean = false
)