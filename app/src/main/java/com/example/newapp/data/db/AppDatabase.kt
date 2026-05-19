package com.example.newapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newapp.data.db.dao.PlaylistDao
import com.example.newapp.data.db.dao.PlaylistTrackDao
import com.example.newapp.data.db.dao.TrackDao
import com.example.newapp.data.db.entity.PlaylistEntity
import com.example.newapp.data.db.entity.PlaylistTrackCrossRef
import com.example.newapp.data.db.entity.TrackEntity

@Database(
    entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackCrossRef::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun playlistTrackDao(): PlaylistTrackDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "playlist_maker.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}