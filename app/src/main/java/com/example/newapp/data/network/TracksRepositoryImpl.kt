package com.example.newapp.data.network

import com.example.newapp.data.db.AppDatabase
import com.example.newapp.data.db.entity.PlaylistTrackCrossRef
import com.example.newapp.data.db.entity.TrackEntity
import com.example.newapp.data.db.entity.toDomain
import com.example.newapp.data.db.entity.toEntity
import com.example.newapp.data.dto.TracksSearchRequest
import com.example.newapp.data.dto.TracksSearchResponse
import com.example.newapp.domain.api.NetworkClient
import com.example.newapp.domain.api.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: AppDatabase,
    private val scope: CoroutineScope,
) : TracksRepository {

    private val trackDao = database.trackDao()
    private val playlistTrackDao = database.playlistTrackDao()
    private val playlistDao = database.playlistDao()

    override suspend fun getAllTracks(): List<Track> {
        return trackDao.getAllTracks().map { it.toDomain() }
    }

    override suspend fun searchTracks(expression: String): List<Track> {
        if (expression.isBlank()) return emptyList()

        val request = TracksSearchRequest(expression)
        val response = networkClient.doRequest(request)

        when {
            response is TracksSearchResponse && response.resultCode == 200 -> {
                return TrackMapper.mapList(response.results)
            }
            response is TracksSearchResponse && response.resultCode == 404 -> {
                return emptyList()
            }
            response.resultCode == -1 -> {
                throw Exception("NO_INTERNET")
            }
            else -> {
                throw Exception("NO_INTERNET")
            }
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return trackDao
            .getTrackByNameAndArtist(track.trackName, track.artistName)
            .map { it?.toDomain() }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavoriteTracks().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val existing = trackDao
            .getTrackByNameAndArtist(track.trackName, track.artistName)
            .firstOrNull()

        if (existing != null) {
            trackDao.updateFavorite(existing.id, isFavorite)
        } else {
            trackDao.insertTrack(track.toEntity().copy(favorite = isFavorite))
        }
    }

    override suspend fun saveTrack(track: Track) {
        val existing = trackDao
            .getTrackByNameAndArtist(track.trackName, track.artistName)
            .firstOrNull()

        val entity = track.toEntity().copy(
            favorite = existing?.favorite ?: false
        )
        trackDao.insertTrack(entity)
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        trackDao.insertTrack(track.toEntity())

        val rowId = playlistTrackDao.insertCrossRef(
            PlaylistTrackCrossRef(
                playlistId = playlistId,
                trackId = track.id
            )
        )
        if (rowId != -1L) {
            playlistDao.incrementTracksCount(playlistId)
        }
    }

    override suspend fun deleteTrackFromPlaylist(track: Track, playlistId: Long) {
        val deletedRows = playlistTrackDao.deleteCrossRef(
            playlistId = playlistId,
            trackId = track.id
        )
        if (deletedRows > 0) {
            playlistDao.decrementTracksCount(playlistId)
        }
    }

    override fun deleteTracksByPlaylistId(playlistId: Long) {
        scope.launch {
            playlistTrackDao.deletePlaylistTracks(playlistId)
        }
    }

    override suspend fun addTrackToPlaylist(track: Track, playlistId: Long) {
        insertTrackToPlaylist(track, playlistId)
    }
}