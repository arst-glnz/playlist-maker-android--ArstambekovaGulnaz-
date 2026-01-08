package com.example.newapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newapp.creator.Creator
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.ui.search.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val tracksRepository: TracksRepository
) : ViewModel() {
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState  = _searchScreenState.asStateFlow()

    fun search(whatSearch: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                val list = tracksRepository.searchTracks(expression = whatSearch)
                _searchScreenState.update { SearchState.Success(list)} //foundList = list) }
            } catch (e: IOException){
                _searchScreenState.update { SearchState.Fail(e.message.toString()) }
            }
        }
    }


    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository()) as T
                }
            }
    }
}