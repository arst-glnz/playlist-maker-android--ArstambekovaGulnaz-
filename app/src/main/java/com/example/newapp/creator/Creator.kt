package com.example.newapp.creator

import com.example.newapp.data.network.RetrofitNetworkClient
import com.example.newapp.data.network.TracksRepositoryImpl
import com.example.newapp.domain.api.TrackSearchInteractor
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.domain.impl.TrackSearchInteractorImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object Creator {
    private val applicationScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Default
    )
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(
            scope = applicationScope
        )
    }

    fun provideTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}