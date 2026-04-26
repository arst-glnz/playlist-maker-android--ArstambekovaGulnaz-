package com.example.newapp.data.network

import com.example.newapp.data.DatabaseMock
import com.example.newapp.data.dto.TracksSearchRequest
import com.example.newapp.data.dto.TracksSearchResponse
import com.example.newapp.domain.api.NetworkClient
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.data.network.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val scope: CoroutineScope
) : TracksRepository {
    override suspend fun getAllTracks(): List<Track> {
        delay(1000)// Имитируем запрос к серверу
        return listTracks
    }

    private val database = DatabaseMock(
        scope = scope
    )
    override suspend fun searchTracks(expression: String): List<Track> {
        //return database.searchTracks(expression)
        delay(500) // Имитируем задержку поиска
        return listTracks.filter { track ->
            track.trackName.contains(expression, ignoreCase = true) ||
                    track.artistName.contains(expression, ignoreCase = true)
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
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
    ),

    Track(
        id = 4,
        trackName = "Бошетунмай",
        artistName = "Кино",
        trackTime = "4:06",
        image = "",
        favorite = false,
        playlistId = 0
    )
)