package com.example.newapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newapp.creator.Creator
import com.example.newapp.data.network.Track
import com.example.newapp.domain.api.PlaylistsRepository
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.domain.impl.PlaylistsRepositoryImpl
import com.example.newapp.domain.models.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PlaylistsViewModel : ViewModel() {

    // Правильно берём через Creator
    private val tracksRepository: TracksRepository = Creator.getTracksRepository()

    private val playlistsRepository: PlaylistsRepository =
        PlaylistsRepositoryImpl(scope = viewModelScope)

    val playlists: Flow<List<Playlist>> = flow {
        playlistsRepository.getAllPlaylists().collect { playlistList ->
            emit(playlistList)
        }
    }

    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()


    init {
        // Добавляем тестовые плейлисты
        viewModelScope.launch {
            createNewPlayList("Best songs 2021", "Лучшие треки года")
            createNewPlayList("Summer Party", "Для вечеринок")
            createNewPlayList("Morning", "Утреннее настроение")
        }
    }

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description)
        }
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksRepository.insertTrackToPlaylist(track, playlistId)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksRepository.deleteTrackFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track).firstOrNull()
    }

    suspend fun getAllTracks(): List<Track> {
        return tracksRepository.getAllTracks()
    }
}