package com.samm8g.chares.screens

import com.samm8g.chares.data.preferences.CompletedChoreDisplayManager
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.samm8g.chares.R
import com.samm8g.chares.composables.AddChoreDialog
import com.samm8g.chares.composables.ChoreList
import com.samm8g.chares.data.Chore
import com.samm8g.chares.viewmodels.ChoreViewModel
import com.samm8g.chares.viewmodels.SettingsViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: ChoreViewModel, settingsViewModel: SettingsViewModel) {
    val chores by viewModel.allChores.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val completedChoreDisplayDuration by settingsViewModel.completedChoreDisplayDuration.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    val hapticFeedback by settingsViewModel.hapticFeedback.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { 
                if (hapticFeedback) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                showDialog = true 
            }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.home_screen_add_chore))
            }
        }
    ) {
        paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val incompleteChores = chores.filter { !it.isCompleted }
            val completedChores = chores.filter { 
                it.isCompleted && (it.completedAt != null &&
                        (completedChoreDisplayDuration == CompletedChoreDisplayManager.NEVER_IN_MILLIS ||
                                System.currentTimeMillis() - it.completedAt < completedChoreDisplayDuration))
            }

            if (incompleteChores.isEmpty() && completedChores.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_chares_found),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(modifier = Modifier.padding(paddingValues)) {
                    ChoreList(chores = incompleteChores, onChoreCheckedChange = { chore, isChecked ->
                        viewModel.update(chore, isChecked)
                    }, showCompletionDate = true, viewModel = viewModel, settingsViewModel = settingsViewModel)

                    if (completedChores.isNotEmpty()) {
                        HorizontalDivider()
                    }

                    ChoreList(chores = completedChores, onChoreCheckedChange = { chore, isChecked ->
                        viewModel.update(chore, isChecked)
                    }, showCompletionDate = true, viewModel = viewModel, settingsViewModel = settingsViewModel)
                }
            }
        }
    }

    if (showDialog) {
        AddChoreDialog(onDismiss = { showDialog = false }, onChoreAdd = { title ->
            viewModel.insert(Chore(title = title))
            showDialog = false
        }, settingsViewModel = settingsViewModel)
    }
}
