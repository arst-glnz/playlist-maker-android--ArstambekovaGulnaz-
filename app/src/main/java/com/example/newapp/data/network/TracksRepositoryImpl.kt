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
    private val scope: CoroutineScope,
    private val networkClient: NetworkClient
) : TracksRepository {
    private val database = DatabaseMock(scope = scope)
    override suspend fun getAllTracks(): List<Track> {
        return emptyList() // пока пусто, можно потом добавить мок-данные если нужно
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        if (expression.isBlank()) return emptyList()

        val request = TracksSearchRequest(expression)
        val response = networkClient.doRequest(request)

        return if (response.resultCode == 200 && response is TracksSearchResponse) {
            TrackMapper.mapList(response.results)
        } else {
            emptyList()
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
