package com.example.newapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newapp.creator.Creator
import com.example.newapp.domain.api.TrackSearchInteractor
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

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {
    private val tracksRepository: TracksRepository = Creator.getTracksRepository()
    private val interactor: TrackSearchInteractor = Creator.provideTrackSearchInteractor()
    private val searchHistoryRepository = SearchHistoryRepositoryImpl( Creator.getSearchHistoryPreferences() )

    private val _searchQuery = MutableStateFlow("")
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)

    val searchScreenState = _searchScreenState.asStateFlow()

    val historyList = MutableStateFlow<List<String>>(emptyList())


    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(800)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    }
                }
        }

        viewModelScope.launch {
            searchHistoryRepository.getHistoryRequests().collect { list ->
                historyList.value = list
            }
        }
    }

    fun updateQuery(query: String) {
        _searchQuery.value = query
    }

    private fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }

                searchHistoryRepository.addToHistory(
                    com.example.newapp.data.dto.Word(word = request)
                )

                val list = interactor.searchTracks(expression = request)

                list.forEach { track ->
                    tracksRepository.saveTrack(track)
                }

                _searchScreenState.update { SearchState.Success(list = list) }

            } catch (e: Exception) {
                _searchScreenState.update { SearchState.Fail(e.message ?: "Unknown error") }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

    fun removeFromHistory(word: String) {
        searchHistoryRepository.removeFromHistory(word)
    }
    fun clearHistory() {
        searchHistoryRepository.clearHistory()
    }
}