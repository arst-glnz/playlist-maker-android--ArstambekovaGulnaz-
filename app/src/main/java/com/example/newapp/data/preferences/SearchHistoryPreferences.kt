package com.example.newapp.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope = CoroutineScope(
        CoroutineName("search_history") + SupervisorJob()
    )
) {

    private val HISTORY_KEY = stringPreferencesKey("search_history")

    companion object {
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
    }

    fun addEntry(word: String) {
        if (word.isBlank()) return

        scope.launch {
            dataStore.edit { prefs ->
                val currentString = prefs[HISTORY_KEY] ?: ""
                val current = if (currentString.isNotEmpty()) {
                    currentString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                current.remove(word)
                current.add(0, word)

                val limited = current.take(MAX_ENTRIES)
                prefs[HISTORY_KEY] = limited.joinToString(SEPARATOR)
            }
        }
    }

    fun getHistoryRequests(): Flow<List<String>> {
        return dataStore.data.map { prefs ->
            val historyString = prefs[HISTORY_KEY] ?: ""
            if (historyString.isEmpty()) {
                emptyList()
            } else {
                historyString.split(SEPARATOR).filter { it.isNotBlank() }
            }
        }
    }
    fun removeEntry(word: String) {
        scope.launch {
            dataStore.edit { prefs ->
                val currentString = prefs[HISTORY_KEY] ?: ""

                val updatedList = currentString
                    .split(SEPARATOR)
                    .filter { it.isNotBlank() && it != word
                    }
                prefs[HISTORY_KEY] =
                    updatedList.joinToString(SEPARATOR) } } }

    fun clearHistory() {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[HISTORY_KEY] = "" }
        }
    }
}