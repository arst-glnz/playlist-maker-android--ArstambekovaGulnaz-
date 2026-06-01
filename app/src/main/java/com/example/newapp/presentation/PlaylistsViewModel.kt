package com.example.newapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newapp.creator.Creator
import com.example.newapp.data.network.Track
import com.example.newapp.util.CoverStorage
import com.example.newapp.domain.api.PlaylistsRepository
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.domain.models.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PlaylistsViewModel : ViewModel() {

    private val tracksRepository: TracksRepository =
        Creator.getTracksRepository()

    val playlistsRepository: PlaylistsRepository =
        Creator.getPlaylistsRepository()

    val playlists: Flow<List<Playlist>> =
        playlistsRepository.getAllPlaylists()

    val favoriteList: Flow<List<Track>> =
        tracksRepository.getFavoriteTracks()

    private val _selectedTrack =
        MutableStateFlow<Track?>(null)

    val selectedTrack = _selectedTrack

    fun selectTrack(track: Track) {
        _selectedTrack.value = track
    }
    fun createNewPlayList(
        namePlaylist: String,
        description: String,
        coverUrl: String = ""
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedCoverUrl = CoverStorage.persistCover(
                Creator.getApplicationContext(),
                coverUrl
            )
            playlistsRepository.addNewPlaylist(
                namePlaylist,
                description,
                savedCoverUrl
            )
        }
    }
    fun addTrackToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.addTrackToPlaylist(track, playlistId)
        }
    }
    fun deleteTrackFromPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteTrackFromPlaylist(track, playlistId)
        }
    }
    suspend fun insertTrackToPlaylist(
        track: Track,
        playlistId: Long
    ) {
        tracksRepository.insertTrackToPlaylist(
            track,
            playlistId
        )
    }

    suspend fun toggleFavorite(
        track: Track,
        isFavorite: Boolean
    ) {
        tracksRepository.updateTrackFavoriteStatus(
            track,
            isFavorite
        )
    }


    suspend fun deletePlaylistById(id: Long) {

        tracksRepository.deleteTracksByPlaylistId(id)

        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {

        return tracksRepository
            .getTrackByNameAndArtist(track)
            .firstOrNull()
    }

    suspend fun getAllTracks(): List<Track> {

        return tracksRepository.getAllTracks()
    }

    fun removeFromFavorites(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(
                track,
                false
            )
        }
    }
    fun deletePlaylist(playlistId: Long) {
        viewModelScope.launch {
            playlistsRepository.deletePlaylistById(playlistId)
        }
    }
}