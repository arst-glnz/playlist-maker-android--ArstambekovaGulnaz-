package com.example.newapp.domain.api

import com.example.newapp.data.network.Track
import com.example.newapp.domain.models.TrackModel

interface TrackSearchInteractor {
    suspend fun searchTracks(expression: String): List<Track>
    suspend fun loadSomeData(onComplete: () -> Unit, trackId: String)
}