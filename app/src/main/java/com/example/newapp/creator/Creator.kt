package com.example.newapp.creator

import com.example.newapp.data.network.RetrofitNetworkClient
import com.example.newapp.data.network.TracksRepositoryImpl
import com.example.newapp.domain.api.ITunesApiService
import com.example.newapp.domain.api.NetworkClient
import com.example.newapp.domain.api.TrackSearchInteractor
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.domain.impl.TrackSearchInteractorImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

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
        return TracksRepositoryImpl(
            networkClient = networkClient,
            scope = applicationScope
        )
    }

    fun provideTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}