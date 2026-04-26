package com.example.newapp.data

import com.example.newapp.data.dto.Word
import com.example.newapp.data.network.Track
import com.example.newapp.domain.models.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DatabaseMock(val scope: CoroutineScope) {

    private val historyList = mutableListOf<Word>()
    private val _historyUpdates = MutableSharedFlow<Unit>()

    private val playlists = mutableListOf<Playlist>()
    private val tracks = mutableListOf<Track>()
    fun getHistoryRequests(): Flow<List<String>> = _historyUpdates
        .map { historyList.map { it.word } }
        .stateIn(
            scope,
            kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            emptyList()  // Начальное пустое
        )
    fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }
    fun addToHistory(word: Word) {
        historyList.add(word)
        notifyHistoryChanged()
    }

    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500) // Имитируем задержку загрузки из базы данных
        val filteredPlaylists = mutableListOf<Playlist>()
        playlists.forEach { playlist ->
            val playlistTracks = tracks.filter { track ->
                track.playlistId == playlist.id
            }
            filteredPlaylists.add(playlist.copy(tracks = playlistTracks))
        }

        emit(filteredPlaylists.toList())
        delay(100)
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = flow {
        emit(playlists.find { it.id == id })
    }

    fun addNewPlaylist(name: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = name,
                description = description,
                tracks = emptyList()
            )
        )
    }

    fun deletePlaylistById(playlistId: Long) {
        playlists.removeIf { it.id == playlistId }
    }

    fun deleteTrackFromPlaylist(trackId: Long) {
        tracks.removeIf { it.id == trackId }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300) // Имитируем задержку
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        tracks.removeIf { it.playlistId == playlistId }
    }

    fun searchTracks(expression: String): List<Track> {
        return tracks.filter { it.trackName.contains(expression, true) }
    }
}