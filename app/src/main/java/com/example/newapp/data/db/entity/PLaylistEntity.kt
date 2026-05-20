package com.example.newapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newapp.domain.models.Playlist

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverUrl: String = "",
    val tracksCount: Int = 0
)

fun PlaylistEntity.toDomain(): Playlist {
    return Playlist(
        id = id,
        name = name,
        description = description,
        tracks = emptyList(),
        tracksCount = tracksCount
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = id,
        name = name,
        description = description,
        coverUrl = "",
        tracksCount = tracksCount
    )
}
