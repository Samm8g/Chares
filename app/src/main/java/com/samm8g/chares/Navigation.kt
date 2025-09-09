package com.samm8g.chares

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.samm8g.chares.screens.AllHistoryScreen
import com.samm8g.chares.screens.HomeScreen
import com.samm8g.chares.screens.AboutScreen
import com.samm8g.chares.screens.LicenseScreen
import com.samm8g.chares.screens.SettingsScreen
import com.samm8g.chares.viewmodels.ChoreViewModel
import com.samm8g.chares.viewmodels.SettingsViewModel

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