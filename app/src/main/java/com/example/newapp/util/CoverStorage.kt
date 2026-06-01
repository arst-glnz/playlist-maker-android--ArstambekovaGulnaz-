package com.example.newapp.util

import android.content.Context
import android.net.Uri
import java.io.File

object CoverStorage {

    fun persistCover(context: Context, uriString: String): String {
        if (uriString.isBlank()) return ""

        return try {
            val uri = Uri.parse(uriString)
            val coversDir = File(context.filesDir, "covers").apply { mkdirs() }
            val destFile = File(coversDir, "cover_${System.currentTimeMillis()}.jpg")

            context.contentResolver.openInputStream(uri)?.use { input ->
                destFile.outputStream().use { output -> input.copyTo(output) }
            }

            destFile.absolutePath
        } catch (_: Exception) {
            uriString
        }
    }
}
