package com.example.newapp.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.newapp.PlaylistHost
import com.example.newapp.presentation.SearchViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModel.getViewModelFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PlaylistHost(
                navController = navController,
                searchViewModel = searchViewModel,
            )
        }

    }
}