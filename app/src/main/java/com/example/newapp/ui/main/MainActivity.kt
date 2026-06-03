package com.example.newapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.newapp.PlaylistHost
import com.example.newapp.creator.Creator
import com.example.newapp.presentation.PlaylistsViewModel
import com.example.newapp.presentation.SearchViewModel
import com.example.newapp.ui.theme.NewAppTheme

class MainActivity : ComponentActivity() {
    private val searchViewModel: SearchViewModel by viewModels()
    private val playlistsViewModel: PlaylistsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Creator.init(this)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme by Creator.getThemePreferences()
                .isDarkTheme()
                .collectAsState(initial = false)
            val navController = rememberNavController()
            NewAppTheme(darkTheme = isDarkTheme) {
                PlaylistHost(
                    navController = navController,
                    searchViewModel = searchViewModel,
                    playlistsViewModel = playlistsViewModel,
                )
            }
        }

    }
}