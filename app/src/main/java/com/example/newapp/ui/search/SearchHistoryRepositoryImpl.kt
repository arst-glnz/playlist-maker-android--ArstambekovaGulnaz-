package com.example.newapp.ui.search

import com.example.newapp.data.DatabaseMock
import com.example.newapp.data.dto.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(private val scope: CoroutineScope): SearchHistoryRepository {
    private val database = DatabaseMock(scope = scope)

    override suspend fun getHistoryRequests(): Flow<List<String>> {
        return database.getHistoryRequests()
    }

    override fun addToHistory(word: Word) {
        database.addToHistory(word = word)
    }
}