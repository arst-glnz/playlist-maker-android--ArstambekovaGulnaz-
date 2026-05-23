package com.example.newapp.domain.api

import com.example.newapp.data.db.entity.PlaylistWithTracks
import com.example.newapp.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {

    fun getPlaylist(playlistId: Long): Flow<Playlist?>

    fun getAllPlaylists(): Flow<List<Playlist>>

    suspend fun addNewPlaylist(
        name: String,
        description: String,
        coverUrl: String = ""
    )

    suspend fun deletePlaylistById(id: Long)

    fun getPlaylistWithTracks(
        id: Long
    ): Flow<PlaylistWithTracks?>
}