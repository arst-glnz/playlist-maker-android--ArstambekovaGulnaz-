package com.example.newapp.creator

import com.example.newapp.data.network.RetrofitNetworkClient
import com.example.newapp.data.network.TracksRepositoryImpl
import com.example.newapp.domain.api.TrackSearchInteractor
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.domain.impl.TrackSearchInteractorImpl

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }

    fun provideTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTracksRepository())
    }
}