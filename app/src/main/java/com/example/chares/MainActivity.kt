package com.example.chares

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chares.data.preferences.LanguageManager
import com.example.chares.data.preferences.ThemeManager
import com.example.chares.ui.theme.CharesTheme
import com.example.chares.viewmodels.ChoreViewModel
import com.example.chares.viewmodels.ChoreViewModelFactory
import com.example.chares.viewmodels.SettingsViewModel
import com.example.chares.viewmodels.SettingsViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val choreViewModel: ChoreViewModel by viewModels {
        ChoreViewModelFactory((application as ChoreApplication).repository)
    }
    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(ThemeManager(this), LanguageManager(this), (application as ChoreApplication).repository)
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
                                title = { Text(stringResource(R.string.app_name)) },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
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
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddChore : Screen("add_chore")
    object AllHistory : Screen("all_history")
    object Settings : Screen("settings")
    object About : Screen("about")
    object License : Screen("license")
}