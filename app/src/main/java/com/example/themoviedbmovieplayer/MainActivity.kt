package com.example.themoviedbmovieplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.domain.model.Movie
import com.example.moviedetail.MovieDetailScreen
import com.example.movieplayer.MoviePlayerScreen
import com.example.themoviedbmovieplayer.routes.Screen
import com.example.searchmovie.MovieSearchScreen
import com.example.searchmovie.SearchViewModel
import com.example.themoviedbmovieplayer.ui.theme.MoviePlayerTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: com.example.searchmovie.SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviePlayerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Search.route
                ) {
                    composable(Screen.Search.route) {
                        com.example.searchmovie.MovieSearchScreen(
                            searchViewModel = viewModel,
                            onMovieClick = { movieItem ->
                                val movieJson = Uri.encode(Gson().toJson(movieItem))
                                navController.navigate("${Screen.Detail.route}/$movieJson")
                            }
                        )
                    }

                    composable(
                        "${Screen.Detail.route}/{movieJson}",
                        arguments = listOf(navArgument("movieJson") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val movieJson = backStackEntry.arguments?.getString("movieJson")
                        val movieItem = Gson().fromJson(movieJson, com.example.domain.model.Movie::class.java)

                        com.example.moviedetail.MovieDetailScreen(
                            movie = movieItem,
                            onPlayClick = {
                                navController.navigate(Screen.Player.route)
                            }
                        )
                    }

                    composable(Screen.Player.route) {
                        com.example.movieplayer.MoviePlayerScreen(Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}
