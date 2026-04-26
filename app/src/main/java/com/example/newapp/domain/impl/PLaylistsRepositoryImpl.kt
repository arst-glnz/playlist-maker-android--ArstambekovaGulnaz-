package com.example.newapp.domain.impl

import com.example.newapp.data.DatabaseMock
import com.example.newapp.domain.api.PlaylistsRepository
import com.example.newapp.domain.models.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val scope: CoroutineScope
) : PlaylistsRepository {
    private val database = DatabaseMock(
        scope = scope,
    )

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        database.addNewPlaylist(
            name = name,
            description = description
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(playlistId = id)
    }
}