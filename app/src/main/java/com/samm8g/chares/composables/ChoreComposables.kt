package com.samm8g.chares.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.samm8g.chares.R
import com.samm8g.chares.data.Chore
import com.samm8g.chares.viewmodels.ChoreViewModel
import com.samm8g.chares.viewmodels.SettingsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChoreList(chores: List<Chore>, onChoreCheckedChange: (Chore, Boolean) -> Unit, showCompletionDate: Boolean = false, viewModel: ChoreViewModel, settingsViewModel: SettingsViewModel) {
    LazyColumn {
        items(chores, key = { it.id }) {
            chore ->
            ChoreItem(chore = chore, onCheckedChange = { isChecked ->
                onChoreCheckedChange(chore, isChecked)
            }, showCompletionDate = showCompletionDate, viewModel = viewModel, settingsViewModel = settingsViewModel, modifier = if (settingsViewModel.animationsEnabled.collectAsState().value) Modifier.animateItemPlacement() else Modifier)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChoreItem(chore: Chore, onCheckedChange: (Boolean) -> Unit, showCompletionDate: Boolean, viewModel: ChoreViewModel, settingsViewModel: SettingsViewModel, modifier: Modifier = Modifier) {
    var showEditChoreDialog by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    val hapticFeedback by settingsViewModel.hapticFeedback.collectAsState()


    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .defaultMinSize(minHeight = 72.dp)
        .combinedClickable(
            onClick = {
                if (hapticFeedback) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                onCheckedChange(!chore.isCompleted)
            },
            onLongClick = {
                if (hapticFeedback) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                showEditChoreDialog = true
            }
        )
    )
    {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = chore.title)
                if (showCompletionDate && chore.isCompleted && chore.completedAt != null) {
                    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    Text(
                        text = stringResource(id = R.string.chore_completed_at, sdf.format(Date(chore.completedAt))),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Checkbox(checked = chore.isCompleted, onCheckedChange = onCheckedChange)
        }
    }

    if (showEditChoreDialog) {
        EditChoreDialog(
            chore = chore,
            onDismiss = { showEditChoreDialog = false },
            onChoreUpdate = { updatedChore ->
                viewModel.update(updatedChore)
                showEditChoreDialog = false
            },
            onChoreDelete = {
                viewModel.delete(it)
                showEditChoreDialog = false
            },
            settingsViewModel = settingsViewModel
        )
    }
}

@Composable
fun EditChoreDialog(
    chore: Chore,
    onDismiss: () -> Unit,
    onChoreUpdate: (Chore) -> Unit,
    onChoreDelete: (Chore) -> Unit,
    settingsViewModel: SettingsViewModel
) {
    var text by remember { mutableStateOf(chore.title) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    val haptic = LocalHapticFeedback.current
    val hapticFeedback by settingsViewModel.hapticFeedback.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.chore_edit_chore)) },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(id = R.string.chore_title)) }
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        if (hapticFeedback) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        showDeleteConfirmDialog = true
                    }
                ) {
                    Text(stringResource(id = R.string.delete))
                }
                TextButton(
                    onClick = {
                        if (hapticFeedback) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        onDismiss()
                    }
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
                TextButton(
                    onClick = {
                        if (hapticFeedback) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        if (text.isNotBlank()) {
                            onChoreUpdate(chore.copy(title = text))
                        }
                    },
                    enabled = text.isNotBlank()
                ) {
                    Text(stringResource(id = R.string.update))
                }
            }
        }
    )

    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text(text = stringResource(id = R.string.chore_delete_chore)) },
            text = { Text(text = stringResource(id = R.string.chore_delete_confirmation, chore.title)) },
            confirmButton = {
                TextButton(onClick = {
                    if (hapticFeedback) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    onChoreDelete(chore)
                    showDeleteConfirmDialog = false
                    onDismiss()
                }) {
                    Text(stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    if (hapticFeedback) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    showDeleteConfirmDialog = false
                }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun AddChoreDialog(onDismiss: () -> Unit, onChoreAdd: (String) -> Unit, settingsViewModel: SettingsViewModel) {
    var text by remember { mutableStateOf("") }
    val haptic = LocalHapticFeedback.current
    val hapticFeedback by settingsViewModel.hapticFeedback.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.chore_add_new_chore)) },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(id = R.string.chore_title)) }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (hapticFeedback) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    if (text.isNotBlank()) {
                        onChoreAdd(text)
                    }
                },
                enabled = text.isNotBlank()
            ) {
                Text(stringResource(id = R.string.add))
            }
        },
        dismissButton = {
            Button(onClick = { 
                if (hapticFeedback) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                onDismiss() 
            }) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}
