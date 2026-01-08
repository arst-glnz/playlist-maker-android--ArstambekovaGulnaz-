package com.example.newapp.data.network

import com.example.newapp.data.dto.TracksSearchRequest
import com.example.newapp.data.dto.TracksSearchResponse
import com.example.newapp.domain.api.NetworkClient
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.data.network.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode == 200) { // успешный запрос
            return (response as TracksSearchResponse).results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
                Track(it.trackName, it.artistName, trackTime)
            }
        } else {
            return emptyList()
        }
    }
}