package com.example.newapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newapp.presentation.PlaylistsViewModel
import com.example.newapp.presentation.SearchViewModel
import com.example.newapp.ui.Screen
import com.example.newapp.ui.favorite.FavoritesScreen
import com.example.newapp.ui.main.MainScreen
import com.example.newapp.ui.playlist.CreatePlaylistScreen
import com.example.newapp.ui.playlist.PlaylistDetailScreen
import com.example.newapp.ui.playlist.PlaylistsScreen
import com.example.newapp.ui.search.SearchScreen
import com.example.newapp.ui.settings.SettingScreen
import com.example.newapp.ui.track.TrackDetailScreen

@Composable
fun PlaylistHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    playlistsViewModel: PlaylistsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.name
    ) {

        composable(Screen.Main.name) {
            MainScreen(
                onSearchClick = { navController.navigate(Screen.Search.name) },
                onSettingsClick = { navController.navigate(Screen.Settings.name) },
                onPlaylistClick = { navController.navigate( Screen.Playlists.name)},
                onFavoritesClick = { navController.navigate(Screen.Favorites.name) }
            )
        }

        composable(Screen.Search.name) {
            SearchScreen(
                modifier = Modifier.fillMaxSize(),
                searchViewModel = searchViewModel,
                onClick = {
                    //navController.popBackStack()
                    trackId ->
                    if (trackId != null) {
                        navController.navigate("track_detail/$trackId")
                    } else {
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(Screen.Settings.name) {
            SettingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Favorites.name) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack()}
            )
        }

        composable(Screen.Playlists.name) {
            PlaylistsScreen(
                modifier = Modifier,
                playlistsViewModel = playlistsViewModel,
                addNewPlaylist = { navController.navigate("create_playlist") },
                navigateToPlaylist = { playlistId ->
                    navController.navigate("playlist_detail/$playlistId")
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("create_playlist") {
            CreatePlaylistScreen(
                onCreateClick = { name, description ->
                    playlistsViewModel.createNewPlayList(name, description)
                    navController.popBackStack()
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            "playlist_detail/{playlistId}",
            arguments = listOf(navArgument("playlistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            PlaylistDetailScreen(
                playlistId = playlistId,
                playlistsViewModel = playlistsViewModel,
                onBackClick = { navController.popBackStack() },
                onTrackClick = { trackId ->
                    navController.navigate("track_detail/$trackId")
                }
            )
        }

        composable(
            "track_detail/{trackId}",
            arguments = listOf(navArgument("trackId") { type = NavType.LongType })
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getLong("trackId") ?: 0L
            TrackDetailScreen(
                trackId = trackId,
                playlistsViewModel = playlistsViewModel,
                searchViewModel = searchViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}