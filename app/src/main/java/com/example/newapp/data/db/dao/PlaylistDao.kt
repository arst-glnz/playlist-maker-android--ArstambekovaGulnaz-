package com.example.newapp.data.db.dao

import androidx.room.*
import com.example.newapp.data.db.entity.PlaylistEntity
import com.example.newapp.data.db.entity.PlaylistWithTracks
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert
    suspend fun insertPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlists WHERE id = :id LIMIT 1")
    fun getPlaylist(id: Long): Flow<PlaylistEntity?>

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylistById(id: Long)

    @Transaction
    @Query("SELECT * FROM playlists")
    fun getPlaylistsWithTracks(): Flow<List<PlaylistWithTracks>>

    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :id")
    fun getPlaylistWithTracks(id: Long): Flow<PlaylistWithTracks?>

    @Query("""
    DELETE FROM playlist_track_cross_ref
    WHERE playlistId = :playlistId
""")
    suspend fun deletePlaylistTracks(playlistId: Long)
}