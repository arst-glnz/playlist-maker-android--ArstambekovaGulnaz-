package com.example.newapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newapp.data.db.entity.PlaylistTrackCrossRef

@Dao
interface PlaylistTrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(crossRef: PlaylistTrackCrossRef): Long

    @Query("""
        DELETE FROM playlist_track_cross_ref
        WHERE playlistId = :playlistId
        AND trackId = :trackId
    """)
    suspend fun deleteCrossRef(
        playlistId: Long,
        trackId: Long
    ): Int

    @Query("""
        DELETE FROM playlist_track_cross_ref
        WHERE playlistId = :playlistId
    """)
    suspend fun deletePlaylistTracks(playlistId: Long)
}