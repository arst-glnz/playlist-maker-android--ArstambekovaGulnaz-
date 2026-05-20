package com.example.newapp.data.db.entity

import com.example.newapp.data.network.Track

fun TrackEntity.toDomain(): Track {
    return Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        image = image,
        previewUrl = previewUrl,
        favorite = favorite
    )
}

fun Track.toEntity(): TrackEntity {
    return TrackEntity(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        image = image,
        previewUrl = previewUrl,
        favorite = favorite
    )
}