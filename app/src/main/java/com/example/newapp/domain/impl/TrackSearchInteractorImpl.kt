package com.example.newapp.domain.impl

import com.example.newapp.data.network.Track
import com.example.newapp.domain.api.TrackSearchInteractor
import com.example.newapp.domain.api.TracksRepository

class TrackSearchInteractorImpl(private val repository: TracksRepository) : TrackSearchInteractor {

    override suspend fun searchTracks(expression: String): List<Track> {
        return repository.searchTracks(expression)
    }
    override suspend fun loadSomeData(onComplete: () -> Unit, trackId: String) {
    }
}