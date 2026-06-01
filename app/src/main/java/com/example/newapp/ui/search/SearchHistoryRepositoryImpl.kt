package com.example.newapp.ui.search

import com.example.newapp.data.dto.Word
import com.example.newapp.data.preferences.SearchHistoryPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
): SearchHistoryRepository {
    override suspend fun getHistoryRequests(): Flow<List<String>> {
        return preferences.getHistoryRequests()
    }

    override fun addToHistory(word: Word) {
        preferences.addEntry(word.word)
    }

    override fun removeFromHistory(word: String) {
        preferences.removeEntry(word)
    }

    override fun clearHistory() {
        preferences.clearHistory()
    }
}