package com.example.newapp.domain.api

import com.example.newapp.data.network.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun getAllTracks(): List<Track>
    suspend fun searchTracks(expression: String): List<Track>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>
    fun deleteTracksByPlaylistId(playlistId: Long)

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(
        track: Track,
        playlistId: Long
    )

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)

    suspend fun saveTrack(track: Track)
    suspend fun addTrackToPlaylist(track: Track, playlistId: Long)

}
