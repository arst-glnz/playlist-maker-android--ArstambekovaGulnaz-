package com.example.newapp.domain.impl

import com.example.newapp.data.db.AppDatabase
import com.example.newapp.data.db.entity.PlaylistEntity
import com.example.newapp.data.db.entity.PlaylistWithTracks
import com.example.newapp.data.db.entity.toDomain
import com.example.newapp.domain.api.PlaylistsRepository
import com.example.newapp.domain.models.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase
) : PlaylistsRepository {

    private val playlistDao = database.playlistDao()

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getPlaylistsWithTracks().map { playlistsWithTracks ->
            playlistsWithTracks.map { playlistWithTracks ->
                playlistWithTracks.playlist.toDomain(
                    tracksCountOverride = playlistWithTracks.tracks.size
                )
            }
        }
    }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistDao.getPlaylist(playlistId)
            .map { it?.toDomain() }
    }

    override suspend fun addNewPlaylist(
        name: String,
        description: String,
        coverUrl: String
    ) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
                coverUrl = coverUrl
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistDao.deletePlaylistById(id)
    }

    override fun getPlaylistWithTracks(id: Long): Flow<PlaylistWithTracks?> {
        return playlistDao.getPlaylistWithTracks(id)
    }
}
