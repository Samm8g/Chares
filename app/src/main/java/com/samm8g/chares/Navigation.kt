package com.samm8g.chares

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val animationsEnabled by settingsViewModel.animationsEnabled.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = { if (animationsEnabled) slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) + fadeIn(animationSpec = tween(500)) else EnterTransition.None },
        exitTransition = { if (animationsEnabled) slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500)) + fadeOut(animationSpec = tween(500)) else ExitTransition.None },
        popEnterTransition = { if (animationsEnabled) slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500)) + fadeIn(animationSpec = tween(500)) else EnterTransition.None },
        popExitTransition = { if (animationsEnabled) slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500)) + fadeOut(animationSpec = tween(500)) else ExitTransition.None }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = choreViewModel, settingsViewModel = settingsViewModel)
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
