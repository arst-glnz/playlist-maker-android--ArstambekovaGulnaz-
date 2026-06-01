package com.example.newapp.data.network

import com.example.newapp.data.dto.TrackDto

object TrackMapper {
    fun map(dto: TrackDto): Track {
        return Track(
            id = dto.trackId,
            trackName = dto.trackName,
            artistName = dto.artistName,
            trackTime = formatTrackTime(dto.trackTimeMillis),
            image = dto.artworkUrl100 ?: "",
            favorite = false,
        )
    }

    private fun formatTrackTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%d:%02d".format(minutes, seconds)
    }

    fun mapList(dtos: List<TrackDto>): List<Track> = dtos.map { map(it) }
}