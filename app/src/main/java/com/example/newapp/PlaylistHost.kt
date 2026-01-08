package com.example.newapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newapp.presentation.SearchViewModel
import com.example.newapp.ui.Screen
import com.example.newapp.ui.main.MainScreen
import com.example.newapp.ui.search.SearchScreen
import com.example.newapp.ui.settings.SettingScreen

@Composable
fun PlaylistHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.name
    ) {

        composable(Screen.Main.name) {
            MainScreen(
                onSearchClick = { navController.navigate(Screen.Search.name) },
                onSettingsClick = { navController.navigate(Screen.Settings.name) }
            )
        }

        composable(Screen.Search.name) {
            SearchScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = searchViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.name) {
            SettingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}