package com.example.newapp.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ThemePreferences(
    private val dataStore: DataStore<Preferences>,
    private val scope: CoroutineScope = CoroutineScope(
        CoroutineName("theme_prefs") + SupervisorJob()
    ),
) {
    private val darkThemeKey = booleanPreferencesKey("dark_theme")

    fun isDarkTheme(): Flow<Boolean> =
        dataStore.data.map { prefs -> prefs[darkThemeKey] ?: false }

    fun setDarkTheme(enabled: Boolean) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[darkThemeKey] = enabled
            }
        }
    }
}
