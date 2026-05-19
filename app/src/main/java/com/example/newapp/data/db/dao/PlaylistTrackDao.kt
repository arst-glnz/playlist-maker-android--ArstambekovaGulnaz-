package com.example.newapp.data.db.dao

import androidx.room.*
import com.example.newapp.data.db.entity.PlaylistTrackCrossRef

@Dao
interface PlaylistTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: PlaylistTrackCrossRef)

    @Query("""
        DELETE FROM playlist_track_cross_ref
        WHERE playlistId = :playlistId AND trackId = :trackId
    """)
    suspend fun deleteCrossRef(
        playlistId: Long,
        trackId: Long
    )
    @Query("""
    DELETE FROM playlist_track_cross_ref
    WHERE playlistId = :playlistId
""")
    suspend fun deletePlaylistTracks(playlistId: Long)
}