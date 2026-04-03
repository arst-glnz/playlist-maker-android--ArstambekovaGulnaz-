package com.example.newapp.data.network

import com.example.newapp.data.dto.TracksSearchRequest
import com.example.newapp.data.dto.TracksSearchResponse
import com.example.newapp.domain.api.NetworkClient
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.data.network.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    override suspend fun getAllTracks(): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks.filter { it.trackName.lowercase().contains(expression.lowercase()) }
    }
}

val listTracks = listOf(

    Track(
        id = 1,
        trackName = "Звезда по имени Солнце",
        artistName = "Кино",
        trackTime = "2:25",
        image = "",
        favorite = false,
        playlistId = 0
    ),

    Track(
        id = 2,
        trackName = "хорошо",
        artistName = "вышел покурить",
        trackTime = "2:38",
        image = "",
        favorite = true,
        playlistId = 0
    ),

    Track(
        id = 3,
        trackName = "авангард",
        artistName = "вышел покурить",
        trackTime = "2:43",
        image = "",
        favorite = false,
        playlistId = 0
    )
)