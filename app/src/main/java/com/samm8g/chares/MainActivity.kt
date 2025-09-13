package com.samm8g.chares

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.samm8g.chares.data.preferences.CompletedChoreDisplayManager
import com.samm8g.chares.data.preferences.HapticManager
import com.samm8g.chares.data.preferences.LanguageManager
import com.samm8g.chares.data.preferences.ThemeManager
import com.samm8g.chares.ui.theme.CharesTheme
import com.samm8g.chares.viewmodels.ChoreViewModel
import com.samm8g.chares.viewmodels.ChoreViewModelFactory
import com.samm8g.chares.viewmodels.SettingsViewModel
import com.samm8g.chares.viewmodels.SettingsViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val choreViewModel: ChoreViewModel by viewModels {
        ChoreViewModelFactory((application as ChoreApplication).repository)
    }
    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(ThemeManager(this), LanguageManager(this), HapticManager(this), CompletedChoreDisplayManager(this), (application as ChoreApplication).repository)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val theme by settingsViewModel.theme.collectAsState()
            val dynamicTheme by settingsViewModel.dynamicTheme.collectAsState()
            val darkTheme = when (theme) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }
            CharesTheme(darkTheme = darkTheme, dynamicColor = dynamicTheme) {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val haptic = LocalHapticFeedback.current
                val hapticFeedback by settingsViewModel.hapticFeedback.collectAsState()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(windowInsets = WindowInsets.systemBars) {
                            Text(
                                text = stringResource(R.string.app_name),
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                            NavigationDrawerItem(
                                label = { Text(stringResource(R.string.navigation_drawer_home)) },
                                selected = currentRoute == Screen.Home.route,
                                onClick = {
                                    if (hapticFeedback) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                    scope.launch { drawerState.close() }
                                }
                            )

                            NavigationDrawerItem(
                                label = { Text(stringResource(R.string.navigation_drawer_all_history)) },
                                selected = currentRoute == Screen.AllHistory.route,
                                onClick = {
                                    if (hapticFeedback) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    navController.navigate(Screen.AllHistory.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text(stringResource(R.string.navigation_drawer_settings)) },
                                selected = currentRoute == Screen.Settings.route,
                                onClick = {
                                    if (hapticFeedback) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    navController.navigate(Screen.Settings.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text(stringResource(R.string.navigation_drawer_about)) },
                                selected = currentRoute == Screen.About.route,
                                onClick = {
                                    if (hapticFeedback) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    }
                                    navController.navigate(Screen.About.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(stringResource(getTitleResId(currentRoute))) },
                                navigationIcon = {
                                    IconButton(onClick = { 
                                        if (hapticFeedback) {
                                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        }
                                        scope.launch { drawerState.open() } 
                                    }) {
                                        Icon(Icons.Filled.Menu, contentDescription = stringResource(R.string.navigation_drawer_menu))
                                    }
                                }
                            )
                        },
                        contentWindowInsets = WindowInsets(0.dp)
                    ) {
                        paddingValues ->
                        AppNavigation(navController = navController, choreViewModel = choreViewModel, settingsViewModel = settingsViewModel, modifier = Modifier.padding(paddingValues))
                    }
                }
            }
        }
    }

    @StringRes
    private fun getTitleResId(currentRoute: String?): Int {
        return when (currentRoute) {
            Screen.Home.route -> R.string.screen_title_home
            Screen.Settings.route -> R.string.screen_title_settings
            Screen.AllHistory.route -> R.string.screen_title_all_history
            Screen.About.route -> R.string.screen_title_about
            Screen.License.route -> R.string.screen_title_license
            else -> R.string.app_name // Default title
        }
    }
}

