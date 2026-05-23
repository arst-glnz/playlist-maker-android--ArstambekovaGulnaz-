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

fun PlaylistEntity.toDomain(tracksCountOverride: Int? = null): Playlist {
    return Playlist(
        id = id,
        name = name,
        description = description,
        tracks = emptyList(),
        tracksCount = tracksCountOverride ?: tracksCount,
        coverUrl = coverUrl
    )
}

fun Playlist.toEntity(): PlaylistEntity {
    return PlaylistEntity(
        id = id,
        name = name,
        description = description,
        coverUrl = coverUrl,
        tracksCount = tracksCount
    )
}
