package com.example.newapp.ui.search

import com.example.newapp.data.dto.Word
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    suspend fun getHistoryRequests(): Flow<List<String>>

    fun addToHistory(word: Word)
}

