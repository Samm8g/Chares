package com.example.chares

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chares.screens.AllHistoryScreen
import com.example.chares.screens.HomeScreen
import com.example.chares.screens.AboutScreen
import com.example.chares.screens.LicenseScreen
import com.example.chares.screens.SettingsScreen
import com.example.chares.viewmodels.ChoreViewModel
import com.example.chares.viewmodels.SettingsViewModel

@Composable
fun AppNavigation(navController: NavHostController, choreViewModel: ChoreViewModel, settingsViewModel: SettingsViewModel, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = choreViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController, viewModel = settingsViewModel)
        }
        composable(Screen.AllHistory.route) {
            AllHistoryScreen(navController = navController, viewModel = choreViewModel)
        }
        
        composable(Screen.About.route) {
            AboutScreen(navController = navController)
        }
        composable(Screen.License.route) {
            LicenseScreen(navController = navController)
        }
    }
}