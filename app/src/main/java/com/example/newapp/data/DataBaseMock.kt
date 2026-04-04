package com.example.newapp.data

import com.example.newapp.data.dto.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DatabaseMock(val scope: CoroutineScope) {

    private val historyList = mutableListOf<Word>()
    private val _historyUpdates = MutableSharedFlow<Unit>()

    fun getHistoryRequests(): Flow<List<String>> = _historyUpdates
        .map { historyList.map { it.word } }
        .stateIn(
            scope,
            kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            emptyList()  // Начальное пустое
        )
    fun notifyHistoryChanged() {
        scope.launch(Dispatchers.IO) {
            _historyUpdates.emit(Unit)
        }
    }
    fun addToHistory(word: Word) {
        historyList.add(word)
        notifyHistoryChanged()
    }
}