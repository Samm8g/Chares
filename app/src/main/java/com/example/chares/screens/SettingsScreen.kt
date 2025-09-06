
package com.example.chares.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chares.R
import com.example.chares.viewmodels.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel) {
    val theme by viewModel.theme.collectAsState()
    val context = LocalContext.current

    LazyColumn {
        item {
            ListItem(
                headlineContent = { Text(stringResource(id = R.string.settings_screen_import_export)) },
                modifier = Modifier.clickable { /* TODO */ }
            )
        }
        item {
            var expanded by remember { mutableStateOf(false) }
            ListItem(
                headlineContent = { Text(stringResource(id = R.string.settings_screen_language)) },
                supportingContent = {
                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}) {
                        TextField(
                            value = context.resources.configuration.locales[0].displayName,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(text = { Text(stringResource(id = R.string.settings_screen_language_english)) }, onClick = {
                                viewModel.setLanguage("en")
                                expanded = false
                                (context as? ComponentActivity)?.recreate()
                            })
                            DropdownMenuItem(text = { Text(stringResource(id = R.string.settings_screen_language_turkish)) }, onClick = {
                                viewModel.setLanguage("tr")
                                expanded = false
                                (context as? ComponentActivity)?.recreate()
                            })
                        }
                    }
                }
            )
        }
        item {
            ListItem(
                headlineContent = { Text(stringResource(id = R.string.settings_screen_theme)) },
                supportingContent = {
                    Row {
                        OutlinedButton(onClick = { viewModel.setTheme("system") }, enabled = theme != "system") {
                            Text(stringResource(id = R.string.settings_screen_theme_system))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(onClick = { viewModel.setTheme("light") }, enabled = theme != "light") {
                            Text(stringResource(id = R.string.settings_screen_theme_light))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(onClick = { viewModel.setTheme("dark") }, enabled = theme != "dark") {
                            Text(stringResource(id = R.string.settings_screen_theme_dark))
                        }
                    }
                }
            )
        }
        
    }
}
