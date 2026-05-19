package com.example.newapp.domain.impl

import com.example.newapp.data.db.AppDatabase
import com.example.newapp.data.db.entity.PlaylistEntity
import com.example.newapp.domain.api.PlaylistsRepository
import com.example.newapp.domain.models.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase
) : PlaylistsRepository {

    private val playlistDao = database.playlistDao()

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistDao.getPlaylist(playlistId).map { it?.toDomain() }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        playlistDao.insertPlaylist(
            PlaylistEntity(name = name, description = description)
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistDao.deletePlaylistById(id)
    }

    private fun PlaylistEntity.toDomain() = Playlist(
        id = id,
        name = name,
        description = description,
        tracks = emptyList()
    )
}