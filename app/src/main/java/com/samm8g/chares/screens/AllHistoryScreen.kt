package com.samm8g.chares.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samm8g.chares.viewmodels.ChoreViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.samm8g.chares.ChoreApplication
import com.samm8g.chares.R
import com.samm8g.chares.composables.ChoreList
import com.samm8g.chares.data.preferences.HapticManager
import com.samm8g.chares.data.preferences.LanguageManager
import com.samm8g.chares.data.preferences.ThemeManager
import com.samm8g.chares.viewmodels.SettingsViewModel
import com.samm8g.chares.viewmodels.SettingsViewModelFactory

@Composable
fun AllHistoryScreen(navController: NavController, viewModel: ChoreViewModel, settingsViewModel: SettingsViewModel) {
    val chores by viewModel.allChores.collectAsState(initial = emptyList())

    if (chores.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.no_chares_found))
        }
    } else {
        Column(modifier = Modifier.padding(0.dp)) {
            ChoreList(
                chores = chores.sortedByDescending { it.id },
                onChoreCheckedChange = { chore, isChecked ->
                    viewModel.update(chore, isChecked)
                },
                showCompletionDate = true,
                viewModel = viewModel,
                settingsViewModel = settingsViewModel
            )
        }
    }
}
