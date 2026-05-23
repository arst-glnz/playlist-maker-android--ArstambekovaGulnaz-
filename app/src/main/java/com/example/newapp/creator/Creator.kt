package com.example.newapp.creator

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.newapp.data.db.AppDatabase
import com.example.newapp.data.network.RetrofitNetworkClient
import com.example.newapp.data.network.TracksRepositoryImpl
import com.example.newapp.data.preferences.SearchHistoryPreferences
import com.example.newapp.domain.api.*
import com.example.newapp.domain.impl.PlaylistsRepositoryImpl
import com.example.newapp.domain.impl.TrackSearchInteractorImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Extension property
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "playlist_maker_prefs")

object Creator {

    private lateinit var applicationContext: Context
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    fun getApplicationContext(): Context = applicationContext
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val iTunesApiService: ITunesApiService by lazy {
        retrofit.create(ITunesApiService::class.java)
    }

    private val networkClient: NetworkClient by lazy {
        RetrofitNetworkClient(iTunesApiService)
    }

    fun getTracksRepository(): TracksRepository {
        val db = AppDatabase.getInstance(applicationContext)
        return TracksRepositoryImpl(networkClient, db, applicationScope)
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        val db = AppDatabase.getInstance(applicationContext)
        return PlaylistsRepositoryImpl(db)
    }

    fun getSearchHistoryPreferences(): SearchHistoryPreferences {
        return SearchHistoryPreferences(applicationContext.dataStore)
    }

    fun provideTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}