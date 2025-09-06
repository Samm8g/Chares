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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
        SettingsViewModelFactory(ThemeManager(this), LanguageManager(this))
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val theme by settingsViewModel.theme.collectAsState()
            val darkTheme = when (theme) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }
            CharesTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            NavigationDrawerItem(
                                label = { Text("Home") },
                                selected = false,
                                onClick = { 
                                    navController.navigate(Screen.Home.route)
                                    scope.launch { drawerState.close() }
                                }
                            )
                            
                            NavigationDrawerItem(
                                label = { Text("All History") },
                                selected = false,
                                onClick = { 
                                    navController.navigate(Screen.AllHistory.route)
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text("Settings") },
                                selected = false,
                                onClick = { 
                                    navController.navigate(Screen.Settings.route)
                                    scope.launch { drawerState.close() }
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text("About") },
                                selected = false,
                                onClick = { 
                                    navController.navigate(Screen.About.route)
                                    scope.launch { drawerState.close() }
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Chares") },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        }
                    ) {
                        paddingValues ->
                        Surface(
                            modifier = Modifier.padding(paddingValues),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavigation(navController = navController, choreViewModel = choreViewModel, settingsViewModel = settingsViewModel)
                        }
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