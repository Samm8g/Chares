
package com.samm8g.chares.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm8g.chares.R
import com.samm8g.chares.viewmodels.SettingsViewModel
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel) {
    val theme by viewModel.theme.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val exportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) {
        uri: Uri? ->
        uri?.let {
            scope.launch {
                val jsonString = viewModel.exportChoreData()
                context.contentResolver.openOutputStream(uri)?.use {
                    outputStream ->
                    outputStream.write(jsonString.toByteArray())
                }
                Toast.makeText(context, "Chore data exported successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        uri: Uri? ->
        uri?.let {
            scope.launch {
                val stringBuilder = StringBuilder()
                context.contentResolver.openInputStream(uri)?.use {
                    inputStream ->
                    BufferedReader(InputStreamReader(inputStream)).use {
                        reader ->
                        var line: String?
                        while (reader.readLine().also { line = it } != null) {
                            stringBuilder.append(line)
                        }
                    }
                }
                val jsonString = stringBuilder.toString()
                viewModel.importChoreData(jsonString)
                Toast.makeText(context, "Chore data imported successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn {
        item {
            ListItem(
                headlineContent = { Text(stringResource(id = R.string.settings_screen_import_export)) },
                trailingContent = {
                    Row {
                        Button(onClick = { exportLauncher.launch("chares_chores_export.json") }) {
                            Text(stringResource(id = R.string.settings_screen_export_chares))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { importLauncher.launch(arrayOf("application/json")) }) {
                            Text(stringResource(id = R.string.settings_screen_import_chares))
                        }
                    }
                },
                modifier = Modifier.clickable { /* TODO */ }
            )
            Text(
                text = stringResource(id = R.string.settings_screen_import_disclaimer),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }
        item {
            var expanded by remember { mutableStateOf(false) }
            ListItem(
                headlineContent = { Text(stringResource(id = R.string.settings_screen_language)) },
                trailingContent = {
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
                trailingContent = {
                    Row(horizontalArrangement = Arrangement.End) {
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
        item {
            val dynamicTheme by viewModel.dynamicTheme.collectAsState()
            ListItem(
                headlineContent = { Text(stringResource(id = R.string.settings_screen_dynamic_theme)) },
                trailingContent = {
                    Switch(
                        checked = dynamicTheme,
                        onCheckedChange = { viewModel.setDynamicTheme(it) }
                    )
                }
            )
        }
    }
}
