package com.example.newapp.util

fun String.toHighQualityArtworkUrl(): String {
    if (isBlank()) return this
    return replace("100x100bb", "512x512bb")
        .replace("100x100", "512x512")
}
