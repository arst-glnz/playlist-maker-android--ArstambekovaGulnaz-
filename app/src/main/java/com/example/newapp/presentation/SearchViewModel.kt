package com.example.newapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newapp.creator.Creator
import com.example.newapp.data.dto.Word
import com.example.newapp.data.network.TracksRepositoryImpl
import com.example.newapp.domain.api.TracksRepository
import com.example.newapp.ui.search.SearchHistoryRepositoryImpl
import com.example.newapp.ui.search.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(FlowPreview::class)
class SearchViewModel() : ViewModel() {
    private val tracksRepository = TracksRepositoryImpl(
        scope = viewModelScope
    )
    private val searchHistoryRepository = SearchHistoryRepositoryImpl(scope = viewModelScope)
    private val _searchQuery = MutableStateFlow("")
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _historyList = MutableStateFlow<List<String>>(emptyList())
    val historyList = _historyList.asStateFlow()
    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    }
                }
        }

        viewModelScope.launch {
            searchHistoryRepository.getHistoryRequests()
                .collect { list ->
                    _historyList.value = list
                }
        }
    }


    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(Word(word = request))
                val list = tracksRepository.searchTracks(expression = request)
                /*val allTracks = tracksRepository.getAllTracks()
                val list = allTracks.filter { track ->
                    track.trackName.contains(request, ignoreCase = true) ||
                            track.artistName.contains(request, ignoreCase = true)
                }*/
                _searchScreenState.update { SearchState.Success(list = list) }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message.toString()) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

}