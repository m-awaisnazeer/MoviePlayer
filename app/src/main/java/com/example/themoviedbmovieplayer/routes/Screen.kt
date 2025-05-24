package com.example.themoviedbmovieplayer.routes

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object Detail : Screen("detail")
    object Player : Screen("player")
}
