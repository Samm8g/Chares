package com.samm8g.chares

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddChore : Screen("add_chore")
    object AllHistory : Screen("all_history")
    object Settings : Screen("settings")
    object About : Screen("about")
    object License : Screen("license")
}
