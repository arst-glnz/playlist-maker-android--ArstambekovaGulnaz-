package com.example.newapp.data.db.dao

import androidx.room.*
import com.example.newapp.data.db.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("SELECT * FROM tracks WHERE favorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE trackName = :trackName AND artistName = :artistName LIMIT 1")
    fun getTrackByNameAndArtist(trackName: String, artistName: String): Flow<TrackEntity?>

    @Query("UPDATE tracks SET favorite = :isFavorite WHERE id = :trackId")
    suspend fun updateFavorite(trackId: Long, isFavorite: Boolean)

    @Query("SELECT * FROM tracks")
    suspend fun getAllTracks(): List<TrackEntity>
}