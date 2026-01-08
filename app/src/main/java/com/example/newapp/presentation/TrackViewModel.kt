package com.example.newapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newapp.domain.api.TrackSearchInteractor
import com.example.newapp.domain.models.TrackScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch

class TrackViewModel(
    private val trackId: String
) : ViewModel() {

    private var _loadingStateFlow = MutableStateFlow(true)

    // 1

    fun getLoadingStateFlow(): StateFlow<Boolean> = _loadingStateFlow.asStateFlow()

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                //val interactor = (this[APPLICATION_KEY] as MyApplication).provideTracksSearchInteractor()

                TrackViewModel(
                    trackId,
                )
            }
        }
    }

}