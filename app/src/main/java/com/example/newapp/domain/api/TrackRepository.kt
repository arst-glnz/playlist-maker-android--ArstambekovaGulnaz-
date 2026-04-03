package com.example.newapp.domain.api

import com.example.newapp.data.network.Track

interface TracksRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>
}
